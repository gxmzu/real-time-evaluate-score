package com.gxmzu.score.domain.model;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/10/8:30
 * @Description: 登录请求模型
 */
public class LoginBody {

    private String userName;
    private String userPwd;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
}
