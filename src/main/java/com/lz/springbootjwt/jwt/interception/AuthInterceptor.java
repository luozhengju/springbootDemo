package com.lz.springbootjwt.jwt.interception;

import com.lz.springbootjwt.jwt.util.JWTUtil;
import com.lz.springbootjwt.service.PermissionService;
import com.lz.springbootjwt.util.StringUtil;
import com.nimbusds.jose.Payload;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author lzj
 * @create 2019-07-26 17:52
 */
//@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private PermissionService permissionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取请求地址
        String uri = request.getRequestURI();

        //获取需要验证的路径
        List<String> permissionUrls = permissionService.findPermissionUrl();

        String header = request.getHeader("Authorization");
        if(null == header||!header.startsWith("Bearer")){
            return false;
        }
        String token = header.substring("Bearer ".length());
        Payload payload = JWTUtil.getPayload(token);
        JSONObject jsonObject = payload.toJSONObject();
        String userRoles = jsonObject.getAsString("_user_roles");
        List<Integer> roleIds = StringUtil.string2Integer(userRoles);

        if(permissionUrls.contains(uri)){
            List<String> userPermissionUrls = permissionService.findPermissionUrl(roleIds);
            if(userPermissionUrls.contains(uri)){
                return true;
            }else {
                return false;
            }
        }

        return false;

    }
}
