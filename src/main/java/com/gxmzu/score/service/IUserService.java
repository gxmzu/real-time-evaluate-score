package com.gxmzu.score.service;

import com.gxmzu.score.domain.AjaxResult;
import com.gxmzu.score.domain.entity.User;

import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/09/14:31
 * @Description: 用户服务
 */
public interface IUserService {

    /**
     * 获取用户列表
     *
     * @param user 用户查询信息
     * @return 用户列表
     */
    public List<User> getUserList(User user);

    /**
     * 获取用户信息
     *
     * @param user 用户查询信息
     * @return 用户信息
     */
    public User getUserInfo(User user);

    /**
     * 用户登录
     *
     * @param userName 用户名
     * @param password 用户密码
     * @return 用户信息和令牌
     */
    public AjaxResult login(String userName, String password);

    /**
     * 添加活动负责人
     *
     * @param user 活动负责人信息
     * @return 添加结果
     */
    public int addPrincipal(User user);

    /**
     * 添加评委
     *
     * @param num 添加数量（1-30）
     * @return 添加结果
     */
    public int addJudge(int num);
}
