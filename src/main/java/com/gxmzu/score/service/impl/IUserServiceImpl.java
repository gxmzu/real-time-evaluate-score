package com.gxmzu.score.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.gxmzu.score.domain.AjaxResult;
import com.gxmzu.score.domain.entity.Match;
import com.gxmzu.score.domain.entity.UserMatch;
import com.gxmzu.score.exception.ServiceException;
import com.gxmzu.score.mapper.MatchMapper;
import com.gxmzu.score.mapper.UserMapper;
import com.gxmzu.score.mapper.UserMatchMapper;
import com.gxmzu.score.service.IMatchService;
import com.gxmzu.score.service.IUserService;
import com.gxmzu.score.domain.entity.User;
import com.gxmzu.score.service.TokenService;
import com.gxmzu.score.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/09/16:30
 * @Description: 用户服务实现类
 */
@Service
public class IUserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private MatchMapper matchMapper;

    @Resource
    private IMatchService matchService;

    @Resource
    private UserMatchMapper userMatchMapper;

    @Resource
    private TokenService tokenService;

    @Resource
    private RedisCache redisCache;

    Logger logger = LoggerFactory.getLogger(IUserServiceImpl.class);

    @Override
    public List<User> getUserList(User user) {
        return userMapper.selectUserList(user);
    }

    @Override
    public User getUserInfo(User user) {
        return userMapper.selectUserByUserName(user);
    }

    /**
     * 用户登录
     * 登录情形：
     * 1、如果cookie中的第一个键值对中名称为Authorization，则使用cookie登录
     * 2、否则使用用户名密码登录
     *
     * @param userName 用户名
     * @param password 用户密码
     * @return 用户信息和令牌信息
     */
    @Override
    public AjaxResult login(String userName, String password) {
        try {
            HttpServletRequest request = ServletUtils.getRequest();
            Cookie[] cookieList = request.getCookies();
            if (cookieList != null && cookieList.length > 0 && Objects.equals(Constants.COOKIE_PREFIX, cookieList[0].getName())) {
                String token = cookieList[0].getValue();
                User user = tokenService.getUser(token);
                if (user != null && user.getPassword().equals(password)) {
                    tokenService.refreshToken(user);
                    Map<String, Object> tmp = new HashMap<>(2);
                    tmp.put("token", token);
                    tmp.put("userInfo", user);
                    logger.warn("用户名：{}，使用了cookie登录", user.getUserName());
                    return AjaxResult.success(tmp);
                }
            }
        } catch (Exception e) {
            logger.error("无效的cookie，错误信息：{}", e.getMessage());
        }
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            return AjaxResult.error(HttpStatus.LACK_QUERY, "缺少请求参数");
        }
        User u = new User();
        u.setUserName(userName);
        u.setPassword(password);
        User user = getUserInfo(u);
        if (user == null) {
            return AjaxResult.error(HttpStatus.LOGIN_ERROR, "用户不存在/密码错误");
        }
        if (user.getPassword().equals(password)) {
            Map<String, Object> tmp = new HashMap<>(2);
            String token = tokenService.createToken(user);
            tmp.put("token", token);
            tmp.put("userInfo", user);
            logger.info("用户名：{}，使用了账号密码登录", user.getUserName());
            return AjaxResult.success(tmp);
        }
        return AjaxResult.error(HttpStatus.LOGIN_ERROR, "用户不存在/密码错误");
    }


    /**
     * 添加活动负责人信息
     *
     * @param user 活动负责人信息
     * @return 受影响的行数
     */
    @Override
    public int addPrincipal(User user) {
        User u = userMapper.selectUserByUserName(user);
        HttpServletRequest request = ServletUtils.getRequest();
        User creator = tokenService.getUser(request);
        if (u == null && creator != null) {
            user.setCreateBy(creator.getUserName());
            if (!Constants.ADMIN.equals(creator.getUserType())) {
                throw new ServiceException("参数错误", HttpStatus.ERROR_QUERY);
            }
            return userMapper.insertUser(user);
        }
        return 0;
    }

    /**
     * 随机生成评委
     * 将生成的评委的第一位作为主评委
     * 有一个数据添加失败所有数据都添加失败
     * 添加指定范围内的评委数，最大生成数从AfterRunner加载系统配置拿到
     * 随机生成规则：
     * 1、生成账号密码长度的配置从AfterRunner加载系统配置拿到
     * 2、假定至少数量为至少达到多少评委才需要主评委，从AfterRunner加载系统配置拿到
     * 3、如果生成数量大于至少数量，则生成的第一位用户为主评委，其他用户为评委
     * 4、如果生成数量为至少数量，则只生成评委
     * {@link com.gxmzu.score.service.AfterRunner#run}
     *
     * @param num 添加数量
     * @return 受影响的行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addJudge(int num) {
        HttpServletRequest request = ServletUtils.getRequest();
        User creator = tokenService.getUser(request);
        // 拿到用户生成最大数据
        int maxNum = Integer.parseInt(redisCache.getCacheObject(Constants.SCORE_CONFIG_KEY + "user.max_generate_number"));
        int maxlength = Integer.parseInt(redisCache.getCacheObject(Constants.SCORE_CONFIG_KEY + "user.max_generate_username_length"));
        int leastNum = Integer.parseInt(redisCache.getCacheObject(Constants.SCORE_CONFIG_KEY + "user.least_generate_number_to_lead_judge"));
        UserMatch userMatch = new UserMatch();
        userMatch.setUserId(creator.getUserId());
        Match match = matchService.getMatchByUserId(userMatch);
        if (num > maxNum) {
            throw new ServiceException("超过最大生成数量", HttpStatus.ERROR_QUERY);
        } else if (num <= 0) {
            throw new ServiceException("生成数量必须大于0", HttpStatus.ERROR_QUERY);
        }
        int row = 0;
        Object savePoint = null;
        try {
            savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
            for (int i = 0; i < num; i++) {
                User user = new User();
                user.setUserName(RandomUtil.randomNumbers(maxlength));
                user.setPassword(RandomUtil.randomNumbers(maxlength));
                if (i == 0 && num > leastNum) {
                    user.setUserType(Constants.LEAD_JUDGE);
                    user.setNickName(match.getMatchName() + (i + 1) + "号" + "主评委");
                } else {
                    user.setUserType(Constants.JUDGE);
                    user.setNickName(match.getMatchName() + (i + 1) + "号" + "评委");
                }
                user.setCreateBy(creator.getNickName());
                if (userMapper.selectUserByUserName(user) != null) {
                    i--;
                    continue;
                }
                if (userMapper.insertUser(user) > 0) {
                    row++;
                } else {
                    throw new Exception();
                }
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
        }
        return row;
    }
}
