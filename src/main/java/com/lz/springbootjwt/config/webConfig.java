package com.lz.springbootjwt.config;

import com.lz.springbootjwt.jwt.interception.AuthInterceptor;
import com.lz.springbootjwt.jwt.interception.JWTInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lzj
 * @create 2019-07-24 16:14
 */
//@Configuration
public class webConfig implements WebMvcConfigurer {

    @Autowired
    private JWTInterceptor jwtInterception;

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterception).addPathPatterns("/**/*.do")
                .excludePathPatterns("/login");
        registry.addInterceptor(authInterceptor).addPathPatterns("/**/*.do")
                .excludePathPatterns("/login");
    }
}
