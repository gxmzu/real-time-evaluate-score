package com.gxmzu.score.interceptor;

import com.gxmzu.score.domain.entity.User;
import com.gxmzu.score.exception.AccessDeniedException;
import com.gxmzu.score.service.TokenService;
import com.gxmzu.score.service.VerifyUser;
import com.gxmzu.score.utils.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(GlobalInterceptor.class);

    @Resource
    private TokenService tokenService;

    @Resource
    private VerifyUser verifyUser;

    /**
     * 拦截所有请求，只有符合要求才放行
     * {@link com.gxmzu.score.config.SpringMvcConfig} 设置匿名访问
     * {@link com.gxmzu.score.service.VerifyUser}查看对应的角色接口权限
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理器
     * @return 返回true表示通过该请求
     * @throws Exception 异常处理
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = tokenService.getUser(request);
        String url = request.getRequestURI();
        if (user == null) {
            throw new AccessDeniedException("未认证", HttpStatus.UNAUTHENTICATED);
        }
        //验证用户请求有效性，只有所属的角色才可以使用对应的接口
        if (!verifyUser.verify(url, user)) {
            log.error("用户：{" + user.getUserName() + "}请求越权接口");
            throw new AccessDeniedException("未授权", HttpStatus.UNAUTHORIZED);
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
