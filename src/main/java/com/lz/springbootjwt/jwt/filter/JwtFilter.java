package com.lz.springbootjwt.jwt.filter;

import com.lz.springbootjwt.jwt.interception.JWTInterceptor;
import com.lz.springbootjwt.jwt.model.JWTVerifyEnum;
import com.lz.springbootjwt.jwt.model.JWTVerifyResult;
import com.lz.springbootjwt.jwt.util.JWTUtil;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lzj
 * @create 2019-07-29 15:36
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        System.out.println("filter----->登陆");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
       // HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String token = httpServletRequest.getHeader("Authorization");
        JwtToken jwtToken = new JwtToken(token);
        getSubject(request,response).login(jwtToken);
        return true;
    }


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        boolean allowed = false;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        try {
            allowed = executeLogin(request, httpServletResponse);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            //throw new AuthorizationException(e);
            //return false;
            httpServletResponse.setStatus(Integer.parseInt(e.getMessage()));
        }
        return allowed;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
       // HttpServletResponse httpResponse = (HttpServletResponse) response;
        //httpResponse.setStatus(601);
        return false;
    }
}
