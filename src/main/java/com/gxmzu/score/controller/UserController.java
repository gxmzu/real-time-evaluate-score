package com.gxmzu.score.controller;

import com.gxmzu.score.domain.AjaxResult;
import com.gxmzu.score.domain.entity.User;
import com.gxmzu.score.domain.model.LoginBody;
import com.gxmzu.score.service.UserService;
import com.gxmzu.score.utils.HttpStatus;
import com.gxmzu.score.utils.RedisCache;
import com.gxmzu.score.utils.StringUtils;
import org.apache.ibatis.annotations.Update;
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
    private UserService userService;

    @Resource
    private RedisCache redisCache;

    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        if (StringUtils.isEmpty(loginBody.getUserName()) || StringUtils.isEmpty(loginBody.getUserPwd())) {
            return AjaxResult.error(HttpStatus.LACK_QUERY, "缺少请求参数");
        }
        return userService.login(loginBody.getUserName(), loginBody.getUserPwd());
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

    /**
     *
     * @param number 生成随机用户
     * @return 用户列表
     */
    @GetMapping("/generate")
    public AjaxResult randomGenerateUser(HttpServletRequest httpServletRequest,int number){
        return userService.randomGenerateUser(httpServletRequest,number);
    }

    @PutMapping("/update")
    public AjaxResult updateUser(HttpServletRequest httpServletRequest,User user){
        return userService.updateUser(httpServletRequest,user);
    }

    @PostMapping("/reset")
    public AjaxResult resetPassword(HttpServletRequest httpServletRequest,User user){
        return userService.resetPassword(httpServletRequest,user);
    }

    @DeleteMapping("/{userId}")
    public AjaxResult deleteUser(HttpServletRequest httpServletRequest, @PathVariable Long userId){
        return userService.deleteUser(httpServletRequest,userId);
    }
}
