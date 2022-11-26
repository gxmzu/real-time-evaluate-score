package com.gxmzu.score.domain.model;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/10/8:30
 * @Description: 登录请求模型
 */
public class LoginBody {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
