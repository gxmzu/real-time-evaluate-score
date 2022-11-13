package com.gxmzu.score.exception;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/13
 * @Description: 权限校验异常类
 */
public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException(){}

    public AccessDeniedException(String msg) {
        super(msg);
    }

    public AccessDeniedException(String msg, Throwable t) {
        super(msg, t);
    }
}
