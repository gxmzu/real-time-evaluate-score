package com.gxmzu.score.exception;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/10/0:11
 * @Description: 工具类异常
 */
public class UtilException extends RuntimeException {

    public UtilException(Throwable e) {
        super(e.getMessage(), e);
    }

    public UtilException(String message) {
        super(message);
    }
}
