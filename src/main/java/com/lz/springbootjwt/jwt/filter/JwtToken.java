package com.lz.springbootjwt.jwt.filter;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author lzj
 * @create 2019-07-29 18:44
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token){
        this.token=token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
