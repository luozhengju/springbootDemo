package com.lz.springbootjwt.controller;

import com.lz.springbootjwt.jwt.model.JWTUser;
import com.lz.springbootjwt.jwt.model.JWTVerifyResult;
import com.lz.springbootjwt.jwt.util.JWTUtil;
import com.lz.springbootjwt.model.LoginResponse;
import com.lz.springbootjwt.model.ResponseEntity;
import com.lz.springbootjwt.model.User;
import com.lz.springbootjwt.model.UserVo;
import com.lz.springbootjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static sun.jvm.hotspot.code.CompressedStream.L;

/**
 * @author lzj
 * @create 2019-07-22 17:03
 */

@RestController
public class UserControlle {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public ResponseEntity<LoginResponse> login(UserVo userVo){
        List<User> users = userService.selectByName(userVo.getUserName());

        if(null == users){
            return ResponseEntity.fail(null, "账号不存在");
        }
        if(users.size()>1){
            return ResponseEntity.fail(null, "账号异常");
        }
        User user = users.get(0);
        if(user.getPassword().equals(userVo.getPassword())){
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setUserId(user.getUserId());
            loginResponse.setUserName(user.getUserName());
            //签发jwt
            JWTUser jwtUser = new JWTUser();
            jwtUser.setUserId(user.getUserId());
            jwtUser.setUserName(user.getUserName());
            String jwtToken = JWTUtil.sign(jwtUser, (long) (1000 * 60 * 60 * 0.5));
            loginResponse.setJwtToken(jwtToken);
            return ResponseEntity.success(loginResponse, "登陆成功");
        }else {
            return ResponseEntity.fail(null, "账号或密码错误");
        }
    }

    @RequestMapping("/test/{jwtToken}")
    public String testJwtVerify(@PathVariable("jwtToken") String jwtToken){
        JWTVerifyResult verify = JWTUtil.verify(jwtToken);
        return verify.getMessage();
    }
}
