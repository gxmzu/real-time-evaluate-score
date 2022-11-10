package com.gxmzu.score.domain;

import java.io.Serializable;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/09/17:29
 * @Description: 通用实体对象
 */
public class BaseEntity extends PageDomain implements Serializable {

    /**
     * 令牌
     */
    private String token;

    /**
     * 过期时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }
}
