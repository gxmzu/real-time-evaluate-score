package com.gxmzu.score.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.gxmzu.score.domain.AjaxResult;
import com.gxmzu.score.mapper.UserMapper;
import com.gxmzu.score.service.UserService;
import com.gxmzu.score.domain.entity.User;
import com.gxmzu.score.service.TokenService;
import com.gxmzu.score.utils.Constants;
import com.gxmzu.score.utils.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/09/16:30
 * @Description: 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private TokenService tokenService;

    /**
     * 管理员id
     */
    @Value("${config.user.adminUserId}")
    private Long adminUserId;

    /**
     * 最大生成用户数量
     */
    @Value("${config.user.maxGenerateNumber}")
    private int maxGenerateNumber;

    /**
     * 用户名最大长度
     */
    @Value("${config.user.maxUserNameLength}")
    private int maxUserNameLength;

    @Override
    public List<User> getUserList(User user) {
        return userMapper.selectUserList(user);
    }

    @Override
    public User getUserInfo(User user) {
        return userMapper.selectUserByUserName(user);
    }

    @Override
    public AjaxResult login(String userName, String userPwd) {
        User user = new User();
        user.setUserName(userName);
        user.setUserPwd(userPwd);
        user = getUserInfo(user);
        if (user == null) {
            return AjaxResult.error(HttpStatus.LOGIN_ERROR, "用户不存在/密码错误");
        }
        Map<String, Object> tmp = new HashMap<>(2);
        String token = tokenService.createToken(user);
        tmp.put("token", token);
        tmp.put("userInfo", user);
        return AjaxResult.success(tmp);
    }

    @Override
    public AjaxResult resetPassword(HttpServletRequest httpServletRequest, User user) {
        //验证参数
        if (user == null || user.getUserName() == null || user.getUserType() == null || user.getUserPwd() == null) {
            return AjaxResult.error(HttpStatus.LACK_QUERY, "缺少请求参数");
        }
        if (!Constants.ROLES.contains(user.getUserType())) {
            return AjaxResult.error(HttpStatus.LACK_QUERY, "缺少请求参数");
        }
        //验证权限
        User userVerify = tokenService.getUser(httpServletRequest);
        //不是活动负责人不可修改其他人密码
        if (!Constants.PRINCIPAL.equals(userVerify.getUserType())) {
            return AjaxResult.error(HttpStatus.UNAUTHORIZED, "未授权此功能");
        }
        //活动负责人不可修改管理员密码
        if (Constants.PRINCIPAL.equals(userVerify.getUserType()) && Constants.ADMIN.equals(user.getUserType())) {
            return AjaxResult.error(HttpStatus.UNAUTHORIZED, "未授权此功能");
        }
        int resetFlag = userMapper.resetPassword(user);
        if (resetFlag == 0) {
            //没修改成功
            return AjaxResult.error(HttpStatus.ERROR, "修改失败，请联系管理员");
        } else if (resetFlag == -1) {
            return AjaxResult.error(HttpStatus.ERROR_QUERY, "请求参数有误");
        } else {
            return AjaxResult.success();
        }
    }

    @Override
    public AjaxResult deleteUser(HttpServletRequest httpServletRequest, Long userId) {
        //验证权限
        User userVerify = tokenService.getUser(httpServletRequest);
        // 主评委，评委不可删除用户
        if (Constants.LEAD_JUDGE.equals(userVerify.getUserType()) || Constants.JUDGE.equals(userVerify.getUserType())) {
            return AjaxResult.error(HttpStatus.UNAUTHORIZED, "未授权此功能");
        }
        // 不可自己删除自己
        if (Constants.PRINCIPAL.equals(userVerify.getUserType())) {
            return AjaxResult.error(HttpStatus.UNAUTHORIZED, "不可删除自己");
        }
        if (adminUserId.equals(userId)) {
            return AjaxResult.error(HttpStatus.UNAUTHORIZED, "不可越权");
        }
        int deleteFlag = userMapper.deleteUser(userId);
        if (deleteFlag == 0) {
            //没删除成功
            return AjaxResult.error(HttpStatus.ERROR, "删除失败，请联系管理员");
        } else if (deleteFlag == -1) {
            return AjaxResult.error(HttpStatus.ERROR_QUERY, "请求参数有误");
        } else {
            return AjaxResult.success();
        }
    }

    @Override
    public AjaxResult randomGenerateUser(HttpServletRequest httpServletRequest, int number) {
        //验证权限
        User userVerify = tokenService.getUser(httpServletRequest);
        if (Constants.LEAD_JUDGE.equals(userVerify.getUserType()) || Constants.JUDGE.equals(userVerify.getUserType())) {
            return AjaxResult.error(HttpStatus.UNAUTHORIZED, "未授权此功能");
        }
        if (number < 0 || number > maxGenerateNumber) {
            return AjaxResult.error(HttpStatus.ERROR_QUERY, "number应在1~200之间");
        }
        String userRandomName;
        String userRandomPwd;
        String userPermissions = Constants.JUDGE;
        // 根据请求用户的用户类型判断生成用户类型
        if (Constants.ADMIN.equals(userVerify.getUserType())) {
            userPermissions = Constants.PRINCIPAL;
        } else if (Constants.PRINCIPAL.equals(userVerify.getUserType())) {
            userPermissions = Constants.JUDGE;
        }
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            //生成随机账号
            userRandomName = RandomUtil.randomNumbers(6);
            //生成随机密码
            userRandomPwd = RandomUtil.randomStringWithoutStr(6, "");
            //存数据进临时user
            User user = new User();
            user.setUserName(userRandomName);
            user.setUserPwd(userRandomPwd);
            user.setUserType(userPermissions);
            //判断在数据库中是否存在该账号
            if (userMapper.selectUserByUserName(user) == null) {
                userList.add(user);
                addUser(user);
            } else {
                i--;
            }
        }
        if (userList.isEmpty()) {
            //为空则创建失败
            return AjaxResult.error(HttpStatus.ERROR, "无法生成用户");
        } else {
            //不为空则创建成功
            return AjaxResult.success();
        }
    }

    @Override
    public AjaxResult updateUser(HttpServletRequest httpServletRequest, User user) {
        //验证权限
        User userVerify = tokenService.getUser(httpServletRequest);
        // 主评委，评委不可修改用户
        if (Constants.LEAD_JUDGE.equals(userVerify.getUserType()) || Constants.JUDGE.equals(userVerify.getUserType())) {
            return AjaxResult.error(HttpStatus.UNAUTHORIZED, "未授权此功能");
        }
        // 不能修改管理员
        if (Constants.ADMIN.equals(user.getUserType())) {
            return AjaxResult.error(HttpStatus.UNAUTHORIZED, "不能越权修改");
        }
        int updateFlag = userMapper.updateUser(user);
        if (updateFlag == 0) {
            //没修改成功
            return AjaxResult.error(HttpStatus.ERROR, "修改失败，请联系管理员");
        } else if (updateFlag == -1) {
            return AjaxResult.error(HttpStatus.ERROR_QUERY, "请求参数有误");
        } else {
            return AjaxResult.success();
        }
    }

    @Override
    public int addUser(User user) {
        if (user.getUserName().length() != maxUserNameLength) {
            return -1;
        }
        if (user.getUserPwd().length() != maxUserNameLength) {
            return -1;
        }
        return userMapper.addUser(user);
    }
}
