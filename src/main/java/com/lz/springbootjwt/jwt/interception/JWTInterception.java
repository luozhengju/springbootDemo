package com.lz.springbootjwt.jwt.interception;


import com.lz.springbootjwt.jwt.model.JWTVerifyEnum;
import com.lz.springbootjwt.jwt.model.JWTVerifyResult;
import com.lz.springbootjwt.jwt.util.JWTUtil;
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

    public static final Integer SGIN_ERROR = 801; //签名错误

    public static final Integer EXPIRED_JWT = 601; //jwt超时

    public static final Integer SUCCESS = 200;      //认证成功

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
        if(null == header||!header.startsWith("Bearer")){
            response.setStatus(SGIN_ERROR);
            return false;
        }
        String token = header.substring("Bearer ".length());
        JWTVerifyResult verify = JWTUtil.verify(token);
        if(verify.getJwtVerifyEnum() == JWTVerifyEnum.EXPIRED){
            response.setStatus(EXPIRED_JWT);
            return false;
        }
        if(verify.getJwtVerifyEnum() == JWTVerifyEnum.FAIL){
            response.setStatus(SGIN_ERROR);
            return false;
        }
        if(verify.getJwtVerifyEnum() == JWTVerifyEnum.SUCCESS){
            response.setStatus(SUCCESS);
            return true;
        }

        return false;
    }
}
