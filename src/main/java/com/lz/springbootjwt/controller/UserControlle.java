package com.lz.springbootjwt.controller;

import com.lz.springbootjwt.jwt.model.JWTUser;
import com.lz.springbootjwt.jwt.model.JWTVerifyResult;
import com.lz.springbootjwt.jwt.util.JWTUtil;
import com.lz.springbootjwt.model.*;
import com.lz.springbootjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/register",method = {RequestMethod.POST,RequestMethod.GET})
    public ResponseEntity<Boolean> register(RegisterVo registerVo){
        try {
            List<User> users = userService.selectByName(registerVo.getUserName());
            if(null == users||users.size() == 0){
                userService.insertUser(registerVo);
                return ResponseEntity.success(true,"注册成功");
            }
            return ResponseEntity.fail(false, "注册失败");
        }catch (Exception e){
            return ResponseEntity.fail(false, "注册异常");
        }

    }

    @RequestMapping(value = "/login",method = {RequestMethod.POST,RequestMethod.GET})
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

    @GetMapping("/findUserById.do")
    public ResponseEntity<User> findUserById(Integer userId){
       User user = userService.findUserById(userId);
       return ResponseEntity.success(user,"获取用户信息成功");
    }

    @PostMapping("/test/{jwtToken}")
    public String testJwtVerify(@PathVariable("jwtToken") String jwtToken){
        JWTVerifyResult verify = JWTUtil.verify(jwtToken);
        return verify.getMessage();
    }
}
