package com.gxmzu.score.service;

import com.gxmzu.score.domain.entity.User;
import com.gxmzu.score.utils.Constants;
import com.gxmzu.score.utils.RedisCache;
import com.gxmzu.score.utils.StringUtils;
import com.gxmzu.score.utils.uuid.IdUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/09/23:56
 * @Description: 令牌服务
 */
@Service
public class TokenService {

    /**
     * 令牌自定义标识
     */
    @Value("${token.header}")
    private String header;

    /**
     * 令牌秘钥
     */
    @Value("${token.secret}")
    private String secret;

    /**
     * 令牌有效期（默认30分钟）
     */
    @Value("${token.expireTime}")
    private int expireTime;

    @Resource
    private RedisCache redisCache;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    /**
     * 创建令牌
     *
     * @param user 用户信息
     * @return 令牌
     */
    public String createToken(User user) {
        String token = IdUtils.fastUUID();
        user.setToken(token);
        refreshToken(user);
        Map<String, Object> claims = new HashMap<>(1);
        claims.put(Constants.LOGIN_USER_KEY, token);
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param user 用户信息
     */
    public void verifyToken(User user) {
        long expireTime = user.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            refreshToken(user);
        }
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取用户身份信息
     *
     * @param request 用户请求
     * @return 用户信息
     */
    public User getUser(HttpServletRequest request) {
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            Claims claims = parseToken(token);
            String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
            String userKey = Constants.LOGIN_TOKEN_KEY + uuid;
            return redisCache.getCacheObject(userKey);
        }
        return null;
    }

    /**
     * 刷新令牌有效期
     *
     * @param user 登录信息
     */
    public void refreshToken(User user) {
        user.setLoginTime(System.currentTimeMillis());
        user.setExpireTime(user.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = Constants.LOGIN_TOKEN_KEY + user.getToken();
        redisCache.setCacheObject(userKey, user, expireTime, TimeUnit.MINUTES);
    }

    /**
     * 获取请求token
     *
     * @param request 用户请求
     * @return token 令牌
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX)) {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }

}
