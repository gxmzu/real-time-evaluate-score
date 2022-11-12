package com.gxmzu.score.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.gxmzu.score.domain.AjaxResult;
import com.gxmzu.score.mapper.UserMapper;
import com.gxmzu.score.service.UserService;
import com.gxmzu.score.domain.entity.User;
import com.gxmzu.score.service.TokenService;
import com.gxmzu.score.utils.HttpStatus;
import lombok.extern.slf4j.Slf4j;
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
    public AjaxResult resetPassword(HttpServletRequest httpServletRequest,User user) {
        //验证权限
        User userVerify = tokenService.getUser(httpServletRequest);
        if (userVerify.getUserType().equals("0") || userVerify.getUserType().equals("2") || userVerify.getUserType().equals("3")) {
            return AjaxResult.error(HttpStatus.UNAUTHORIZED, "未授权此功能");
        }
        int resetFlag = userMapper.resetPassword(user);
        if (resetFlag == 0){
            //没修改成功
            return AjaxResult.error(HttpStatus.ERROR,"修改失败，请联系管理员");
        }else if (resetFlag == -1){
            return AjaxResult.error(HttpStatus.ERROR_QUERY,"请求参数有误");
        }else {
            return AjaxResult.success();
        }
    }

    @Override
    public AjaxResult deleteUser(HttpServletRequest httpServletRequest, Long userId) {
        //验证权限
        User userVerify = tokenService.getUser(httpServletRequest);
        if (userVerify.getUserType().equals("0") || userVerify.getUserType().equals("2") || userVerify.getUserType().equals("3")) {
            return AjaxResult.error(HttpStatus.UNAUTHORIZED, "未授权此功能");
        }
        int deleteFlag = userMapper.deleteUser(userId);
        if (deleteFlag == 0){
            //没删除成功
            return AjaxResult.error(HttpStatus.ERROR,"删除失败，请联系管理员");
        }else if (deleteFlag == -1){
            return AjaxResult.error(HttpStatus.ERROR_QUERY,"请求参数有误");
        }else {
            return AjaxResult.success();
        }
    }

    @Override
    public AjaxResult randomGenerateUser(HttpServletRequest httpServletRequest,int number) {
        //验证权限
        User userVerify = tokenService.getUser(httpServletRequest);
        if (userVerify.getUserType().equals("0") || userVerify.getUserType().equals("2") || userVerify.getUserType().equals("3")) {
            return AjaxResult.error(HttpStatus.UNAUTHORIZED, "未授权此功能");
        }
        if (number < 0 || number > 200){
            return AjaxResult.error(HttpStatus.ERROR_QUERY,"number应在1~200之间");
        }
        String userRandomName;
        String userRandomPwd;
        List<User> userList = new ArrayList<>();
        for(int i = 0 ;i < number ; i++){
            //生成随机账号
            userRandomName  = RandomUtil.randomNumbers(6);
            //生成随机密码
            userRandomPwd = RandomUtil.randomStringWithoutStr(6,"");
            //存数据进临时user
            User user = new User();
            user.setUserName(userRandomName);
            user.setUserPwd(userRandomPwd);
            user.setUserType("3");
            //判断在数据库中是否存在该账号
            if (userMapper.selectUserByUserName(user) == null){
                userList.add(user);
                addUser(user);
            }else {
                i--;
            }
        }
        if (userList.isEmpty()){
            //为空则创建失败
            return AjaxResult.error(1,"无法生成用户");
        }else{
            //不为空则创建成功
            return AjaxResult.success();
        }
    }

    @Override
    public AjaxResult updateUser(HttpServletRequest httpServletRequest,User user) {
        //验证权限
        User userVerify = tokenService.getUser(httpServletRequest);
        if (userVerify.getUserType().equals("0") || userVerify.getUserType().equals("2") || userVerify.getUserType().equals("3")) {
            return AjaxResult.error(HttpStatus.UNAUTHORIZED, "未授权此功能");
        }
        int updateFlag = userMapper.updateUser(user);
        if (updateFlag == 0){
            //没修改成功
            return AjaxResult.error(HttpStatus.ERROR,"修改失败，请联系管理员");
        }else if (updateFlag == -1){
            return AjaxResult.error(HttpStatus.ERROR_QUERY,"请求参数有误");
        }else {
            return AjaxResult.success();
        }
    }

    @Override
    public int addUser(User user) {
        if (user.getUserName().length() != 6){
            return -1;
        }
        if (user.getUserPwd().length() != 6){
            return -1;
        }
        return userMapper.addUser(user);
    }
}
