package com.gxmzu.score.interceptor;

import com.gxmzu.score.domain.entity.User;
import com.gxmzu.score.exception.AccessDeniedException;
import com.gxmzu.score.service.TokenService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/10/21:00
 * @Description: 全局拦截器
 */
public class GlobalInterceptor implements HandlerInterceptor {

    @Resource
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = tokenService.getUser(request);
        if (user == null) {
            throw new AccessDeniedException("未认证");
        }
        // 验证令牌有效期，只有离过期还差20分钟内查刷新token
        tokenService.verifyToken(user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
