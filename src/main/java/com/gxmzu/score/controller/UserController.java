package com.gxmzu.score.controller;

import com.gxmzu.score.domain.AjaxResult;
import com.gxmzu.score.domain.entity.User;
import com.gxmzu.score.service.UserService;
import com.gxmzu.score.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/09/15:18
 * @Description: 用户接口
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 获取用户列表
     *
     * @param user 用户查询信息
     * @return 用户列表
     */
    @GetMapping("/list")
    public AjaxResult getUserList(User user) {
        startPage(user);
        List<User> list = userService.getUserList(user);
//        redisCache.setCacheList("userList", list);
        return getDataTable(list);
    }
}
