package com.gxmzu.score.utils;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/10/0:03
 * @Description: 常量类
 */
public class Constants {

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * cookie前缀
     */
    public static final String COOKIE_PREFIX = "Authorization";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 系统配置 redis key
     */
    public static final String SCORE_CONFIG_KEY = "score_configs:";

    /**
     * 管理员
     */
    public static final String ADMIN = "0";

    /**
     * 活动负责人
     */
    public static final String PRINCIPAL = "1";

    /**
     * 主评委
     */
    public static final String LEAD_JUDGE = "2";

    /**
     * 评委
     */
    public static final String JUDGE = "3";

    /**
     * 角色组
     */
    public static final List<String> ROLES = Arrays.asList("0", "1", "2", "3");
}
