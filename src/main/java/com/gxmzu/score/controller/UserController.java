package com.gxmzu.score.controller;

import com.gxmzu.score.domain.AjaxResult;
import com.gxmzu.score.domain.entity.User;
import com.gxmzu.score.domain.model.LoginBody;
import com.gxmzu.score.exception.AccessDeniedException;
import com.gxmzu.score.service.TokenService;
import com.gxmzu.score.service.IUserService;
import com.gxmzu.score.utils.Constants;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/09/15:18
 * @Description: 用户接口
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource
    private IUserService userService;

    @Resource
    private TokenService tokenService;


    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        return userService.login(loginBody.getUserName(), loginBody.getPassword());
    }

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
        return getDataTable(list);
    }



}
