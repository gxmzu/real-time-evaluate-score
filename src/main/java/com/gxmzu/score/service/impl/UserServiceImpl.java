package com.gxmzu.score.service.impl;

import com.gxmzu.score.domain.AjaxResult;
import com.gxmzu.score.mapper.UserMapper;
import com.gxmzu.score.service.UserService;
import com.gxmzu.score.domain.entity.User;
import com.gxmzu.score.service.TokenService;
import com.gxmzu.score.utils.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/09/16:30
 * @Description: 用户服务实现类
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private TokenService tokenService;

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
    public int resetPassword() {
        return 0;
    }

    @Override
    public int deleteUser(Long userId) {
        return 0;
    }

    @Override
    public List<User> randomGenerateUser(int number) {
        return null;
    }

    @Override
    public int updateUser(User user) {
        return 0;
    }
}
