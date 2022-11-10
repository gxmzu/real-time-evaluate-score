package com.gxmzu.score.config;

import com.gxmzu.score.interceptor.GlobalInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: https://github.com/gxmzu
 * @Date: 2022/11/10/20:58
 * @Description: 拦截器配置类
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    @Bean
    public HandlerInterceptor getGlobalInterceptor(){
        return new GlobalInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(getGlobalInterceptor());
        registration.addPathPatterns("/**");
        registration.excludePathPatterns(
                "/user/login",
                "/**/*.html",
                "/**/*.js",
                "/**/*.css"
        );
    }
}
