package com.gxmzu.score.exception;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/10/21:08
 * @Description: 业务类异常
 */
public class ServiceException extends RuntimeException {

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    public ServiceException() {
    }

    public ServiceException(String message) {
        this.message = message;
    }

    public ServiceException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

}
