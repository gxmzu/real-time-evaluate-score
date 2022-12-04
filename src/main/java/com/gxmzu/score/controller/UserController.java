package com.gxmzu.score.controller;

import com.gxmzu.score.domain.AjaxResult;
import com.gxmzu.score.domain.entity.User;
import com.gxmzu.score.domain.model.LoginBody;
import com.gxmzu.score.exception.ServiceException;
import com.gxmzu.score.service.IUserService;
import com.gxmzu.score.utils.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        return userService.login(loginBody.getUserName(), loginBody.getPassword());
    }

    @GetMapping("/list")
    public AjaxResult getUserList(User user) {
        startPage(user);
        List<User> list = userService.getUserList(user);
        return getDataTable(list);
    }

    @PostMapping("/add")
    public AjaxResult addPrincipal(User user) {
        int row = userService.addPrincipal(user);
        if (row > 0) {
            return AjaxResult.success();
        }
        return AjaxResult.error(HttpStatus.ERROR,"添加失败，请联系管理员");
    }

    @PostMapping("/generate")
    public AjaxResult generate(@RequestParam int number) throws ServiceException {
        int row = userService.addJudge(number);
        if (row > 0) {
            return AjaxResult.success();
        }
        return AjaxResult.error(HttpStatus.ERROR,"添加失败，请联系管理员");
    }
}
