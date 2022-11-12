package com.gxmzu.score.service;

import com.gxmzu.score.domain.AjaxResult;
import com.gxmzu.score.domain.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/09/14:31
 * @Description: 用户服务
 */
public interface UserService {

    /**
     * 获取用户列表
     * @param user 用户查询信息
     *
     * @return 用户列表
     */
    public List<User> getUserList(User user);

    /**
     * 获取用户列表
     * @param user 用户查询信息
     *
     * @return 用户列表
     */
    public User getUserInfo(User user);

    /**
     * 用户登录
     * @param userName 用户名
     * @param userPwd 用户密码
     * @return 用户信息和令牌
     */
    public AjaxResult login(String userName, String userPwd);

    /**
     * 重置密码
     *
     * @return 重置结果
     */
    public AjaxResult resetPassword(HttpServletRequest httpServletRequest,User user);

    /**
     * 删除用户
     *
     * @param userId 用户id
     * @return 删除结果
     */
    public AjaxResult deleteUser(HttpServletRequest httpServletRequest,Long userId);

    /**
     * 随机生成用户
     *
     * @param number 生成用户数量，范围在1~200之间
     * @return 生成结果
     */
    public AjaxResult randomGenerateUser(HttpServletRequest httpServletRequest,int number);

    /**
     * 修改用户
     *
     * @param user 修改后的用户信息
     * @return 修改结果
     */
    public AjaxResult updateUser(HttpServletRequest httpServletRequest,User user);

    /**
     * 添加用户
     *
     * @param user 添加用户信息
     * @return 修改结果
     */
    public int addUser(User user);
}
