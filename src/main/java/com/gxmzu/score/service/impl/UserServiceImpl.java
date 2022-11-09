package com.gxmzu.score.service.impl;

import com.gxmzu.score.mapper.UserMapper;
import com.gxmzu.score.service.UserService;
import com.gxmzu.score.domain.AjaxResult;
import com.gxmzu.score.domain.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/09/16:30
 * @Description: 用户接口实现类
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getUserList(User user) {
        return userMapper.selectUserList(user);
    }

    @Override
    public AjaxResult login() {
        return null;
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
