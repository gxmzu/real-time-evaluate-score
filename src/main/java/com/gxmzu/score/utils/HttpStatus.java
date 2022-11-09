package com.gxmzu.score.utils;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/09/15:28
 * @Description: 系统服务状态码
 */
public class HttpStatus {

    /**
     * 成功
     */
    public static final int SUCCESS = 200;

    /**
     * 系统错误
     */
    public static final int ERROR = 500;

    /**
     * 未认证
     */
    public static final int UNAUTHENTICATION = 510;

    /**
     * 未授权
     */
    public static final int UNAUTHORIZED = 511;

    /**
     * 缺少请求参数
     */
    public static final int LACK_QUERY = 520;

    /**
     * 错误的请求参数
     */
    public static final int ERROR_QUERY = 521;

    /**
     * 用户不存在/密码错误
     */
    public static final int LOGIN_ERROR = 522;

}
