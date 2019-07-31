package com.lz.springbootjwt.shiro;

import com.lz.springbootjwt.jwt.filter.JwtToken;
import com.lz.springbootjwt.jwt.model.JWTVerifyEnum;
import com.lz.springbootjwt.jwt.model.JWTVerifyResult;
import com.lz.springbootjwt.jwt.util.JWTUtil;
import com.lz.springbootjwt.model.User;
import com.lz.springbootjwt.service.PermissionService;
import com.lz.springbootjwt.service.UserService;
import com.nimbusds.jose.Payload;
import net.minidev.json.JSONObject;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

/**
 * @author lzj
 * @create 2019-07-29 14:14
 */
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public String getName() {
        return "myRealm";
    }



    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String token = (String) principalCollection.getPrimaryPrincipal();
        String jwtToken = token.substring("Bearer ".length());
        Payload payload = JWTUtil.getPayload(jwtToken);

        JSONObject jsonObject = payload.toJSONObject();
        Long userId = jsonObject.getAsNumber("_user_id").longValue();
        Set<String> perms = permissionService.findPermsByUserId(userId);
        //info.addObjectPermissions(perms);
        info.setStringPermissions(perms);
        return info;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证逻辑");
        if(authenticationToken instanceof UsernamePasswordToken){
            //登陆认证
            String loginAccount = (String) authenticationToken.getPrincipal();
            List<User> users = userService.selectByName(loginAccount);
            User user = null;
            if(users != null&&users.size()==1){
                user = users.get(0);
            }
            if(user == null){
                return null;
            }

            return new SimpleAuthenticationInfo(user,user.getPassword(),null,getName());
        }else {//jwttoken认证
            //JwtToken jwtToken = (JwtToken) authenticationToken;
            String jwtToken = (String) authenticationToken.getPrincipal();
            if(null == jwtToken||!jwtToken.startsWith("Bearer")){
                //response.setStatus(SGIN_ERROR);
                //throw new AuthenticationException("jwt格式异常");
                throw new AuthenticationException("801");
            }
            String token = jwtToken.substring("Bearer ".length());
            JWTVerifyResult verify = JWTUtil.verify(token);
            if(verify.getJwtVerifyEnum() == JWTVerifyEnum.EXPIRED){
                //response.setStatus(601);
                //throw new AuthenticationException("jwt超时");
                throw new AuthenticationException("601");
            }
            if(verify.getJwtVerifyEnum() == JWTVerifyEnum.FAIL){
                //response.setStatus(SGIN_ERROR);
                //throw new AuthenticationException("jwt验证错误");
                throw new AuthenticationException("801");
            }
            if(verify.getJwtVerifyEnum() == JWTVerifyEnum.SUCCESS){
                //response.setStatus(SUCCESS);
                return new SimpleAuthenticationInfo(jwtToken, jwtToken, null, getName());
            }
            return null;
        }

    }
}
