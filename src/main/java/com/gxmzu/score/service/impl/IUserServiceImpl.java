package com.gxmzu.score.service.impl;

import com.gxmzu.score.domain.AjaxResult;
import com.gxmzu.score.mapper.UserMapper;
import com.gxmzu.score.service.IUserService;
import com.gxmzu.score.domain.entity.User;
import com.gxmzu.score.service.TokenService;
import com.gxmzu.score.utils.Constants;
import com.gxmzu.score.utils.HttpStatus;
import com.gxmzu.score.utils.ServletUtils;
import com.gxmzu.score.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    private TokenService tokenService;

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
     * <p>
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
                if (user != null) {
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
        u = getUserInfo(u);
        if (u == null) {
            return AjaxResult.error(HttpStatus.LOGIN_ERROR, "用户不存在/密码错误");
        }
        Map<String, Object> tmp = new HashMap<>(2);
        String token = tokenService.createToken(u);
        tmp.put("token", token);
        tmp.put("userInfo", u);
        logger.info("用户名：{}，使用了账号密码登录", u.getUserName());
        return AjaxResult.success(tmp);
    }


}
