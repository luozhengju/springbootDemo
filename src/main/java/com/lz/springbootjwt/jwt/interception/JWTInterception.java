package com.lz.springbootjwt.jwt.interception;


import com.lz.springbootjwt.jwt.model.JWTVerifyResult;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lzj
 * @create 2019-07-23 17:36
 */
@Component
public class JWTInterception extends HandlerInterceptorAdapter {

    /**
     * 请求接口前拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String header = request.getHeader("Authorization");
        System.out.println(header);
        if(header != null&&header.startsWith("Bearer")){


        }else {
            throw new RuntimeException("jwt非法");
        }

        return false;
    }
}
