package com.gxmzu.score.service;

import com.gxmzu.score.domain.AjaxResult;
import com.gxmzu.score.domain.entity.User;

import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/09/14:31
 * @Description: 用户接口
 */
public interface UserService {

    /**
     * 获取用户列表
     *
     * @return 用户列表
     */
    public List<User> getUserList(User user);

    /**
     * 用户登录
     *
     * @return 用户信息和token
     */
    public AjaxResult login();

    /**
     * 重置密码
     *
     * @return 重置结果
     */
    public int resetPassword();

    /**
     * 删除用户
     *
     * @param userId 用户id
     * @return 删除结果
     */
    public int deleteUser(Long userId);

    /**
     * 随机生成用户
     *
     * @param number 生成用户数量，范围在1~200之间
     * @return 生成结果
     */
    public List<User> randomGenerateUser(int number);

    /**
     * 修改用户
     *
     * @param user 修改后的用户信息
     * @return 修改结果
     */
    public int updateUser(User user);

}
