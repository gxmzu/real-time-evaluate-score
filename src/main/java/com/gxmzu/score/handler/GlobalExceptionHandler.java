package com.gxmzu.score.handler;

import com.gxmzu.score.domain.AjaxResult;
import com.gxmzu.score.exception.AccessDeniedException;
import com.gxmzu.score.exception.ServiceException;
import com.gxmzu.score.utils.HttpStatus;
import com.gxmzu.score.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/13
 * @Description: 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    /**
     * 权限校验异常处理
     *
     * @param request 请求
     * @param e       异常
     * @return {@link AjaxResult}
     */
    @ExceptionHandler(AccessDeniedException.class)
    public AjaxResult handlerAccessDeniedException(HttpServletRequest request, AccessDeniedException e) {
        log.error("请求地址：{}，权限校验失败：{}" , request.getRequestURI(), e.getMessage());
        return AjaxResult.error(HttpStatus.UNAUTHENTICATION, "未授权，请联系管理员授权");
    }

    /**
     * 不支持的请求
     *
     * @param request 请求
     * @param e       异常
     * @return {@link AjaxResult}
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public AjaxResult handlerHttpRequestMethodNotSupported(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
        log.error("请求地址：{}，不支持{}请求", request.getRequestURI(), e.getMethod());
        return AjaxResult.error(HttpStatus.ERROR, e.getMessage());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public AjaxResult handleServiceException(ServiceException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        Integer code = e.getCode();
        return StringUtils.isNotNull(code) ? AjaxResult.error(code, e.getMessage()) : AjaxResult.error(HttpStatus.ERROR, e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public AjaxResult handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, e);
        return AjaxResult.error(HttpStatus.ERROR, e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestURI, e);
        return AjaxResult.error(HttpStatus.ERROR, e.getMessage());
    }
}
