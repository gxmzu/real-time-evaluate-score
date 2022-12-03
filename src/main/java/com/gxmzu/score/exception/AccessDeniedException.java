package com.gxmzu.score.exception;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/13
 * @Description: 权限校验异常类
 */
public class AccessDeniedException extends RuntimeException {

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    public AccessDeniedException() {
    }

    public AccessDeniedException(String msg, Integer code) {
        this.code = code;
        this.message = msg;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
