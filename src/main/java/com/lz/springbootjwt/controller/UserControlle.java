package com.lz.springbootjwt.controller;

import com.lz.springbootjwt.jwt.model.JWTUser;
import com.lz.springbootjwt.jwt.model.JWTVerifyResult;
import com.lz.springbootjwt.jwt.util.JWTUtil;
import com.lz.springbootjwt.model.*;
import com.lz.springbootjwt.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

import static sun.jvm.hotspot.code.CompressedStream.L;

/**
 * @author lzj
 * @create 2019-07-22 17:03
 */

@RestController
@Slf4j
@RequestMapping("/user")
@Api(value = "用户管理",description = "用户管理")
public class UserControlle {

    @Autowired
    private UserService userService;

    //Logger log = LoggerFactory.getLogger(getClass());


    @ApiOperation("用户注册")
    @RequestMapping(value = "/register",method = {RequestMethod.POST,RequestMethod.GET})
    public ResponseEntity<Boolean> register(RegisterVo registerVo){
        log.info("用户管理|用户注册|register|registerVo:{}",registerVo);
        try {
            List<User> users = userService.selectByName(registerVo.getLoginAccount());
            if(null == users||users.size() == 0){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                User user = new User();
                BeanUtils.copyProperties(registerVo, user);
                user.setCreateTime(sdf.format(new Date()));
                userService.insertUser(user);

                return ResponseEntity.success(true,"注册成功");
            }
            return ResponseEntity.fail(false, "注册失败");
        }catch (Exception e){
            return ResponseEntity.fail(false, "注册异常");
        }

    }

    @RequestMapping(value = "/login",method = {RequestMethod.POST,RequestMethod.GET})
    @ApiOperation("用户登陆")
    public ResponseEntity<LoginResponse> login(UserVo userVo){
        log.info("用户管理|用户登陆|login|userVo:{}",userVo);
        List<User> users = userService.selectByName(userVo.getLoginAccount());
        if(null == users){
            return ResponseEntity.fail(null, "账号不存在");
        }
        if(users.size()>1){
            return ResponseEntity.fail(null, "账号异常");
        }
        User user = users.get(0);
       List<Integer> roleIds = userService.findRoleByUserId(user.getId());
        String roleStr = StringUtils.join(roleIds, ",");
        if(user.getPassword().equals(userVo.getPassword())){
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setUserId(user.getId());
            loginResponse.setUserName(user.getUserName());
            //签发jwt
            JWTUser jwtUser = new JWTUser();
            jwtUser.setUserId(user.getId());
            jwtUser.setUserName(user.getUserName());
            jwtUser.setRoles(roleStr);
            String jwtToken = JWTUtil.sign(jwtUser, (long) (1000 * 60 * 60 * 0.5));
            loginResponse.setJwtToken(jwtToken);
            return ResponseEntity.success(loginResponse, "登陆成功");
        }else {

            return ResponseEntity.fail(null, "账号或密码错误");
        }
    }



    @PostMapping(value = "/login.do")
    public ResponseEntity<LoginResponse> loginShiro(UserVo userVo){

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userVo.getLoginAccount(), userVo.getPassword());
        try {
            subject.login(token);
            LoginResponse loginResponse = new LoginResponse();
            return ResponseEntity.success(loginResponse, "登陆成功");
        }catch (UnknownAccountException e){

            e.printStackTrace();
            return ResponseEntity.fail(null, "用户名不存在");
        }catch (IncorrectCredentialsException e){
            return ResponseEntity.fail(null,"密码错误");
        }catch (AuthenticationException e) {
            e.printStackTrace();
            return ResponseEntity.fail(null, "未知错误");
        }
    }

    @GetMapping("/findUser.do")
    @ApiOperation("查询用户信息")
    @RequiresPermissions("user:find")
    public ResponseEntity<User> findUserById(Long id){
        log.info("用户管理|查询用户信息|findUserById|id:{}",id);
        try {
            User user = userService.findUserById(id);
            if(user == null){
                return ResponseEntity.fail(null, "获取用户信息失败");
            }
            return ResponseEntity.success(user,"获取用户信息成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取用户信息异常", e);
            return ResponseEntity.fail(null, "获取用户信息异常"+e);
        }
    }

    @PostMapping("/updateUser.do")
    public ResponseEntity<Boolean> updataUser(UserupdataVo userupdataVo){
        try {
            User user = new User();
            BeanUtils.copyProperties(userupdataVo, user);
            userService.updataUser(user);
            return ResponseEntity.success(true,"用户信息更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.fail(false, "用户信息更新失败");
        }
    }

    @PostMapping("/deleteUser.do")
    public ResponseEntity<ResultEnum> deleteUser(String ids){
        if(ids == null){
            return ResponseEntity.fail(ResultEnum.FAIL, "请输入需要删除的id");
        }
        try {
            userService.deleteUser(ids);
            return ResponseEntity.success(ResultEnum.SUCCESS,"用户删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.fail(ResultEnum.FAIL, "删除操作异常");
        }
    }

    /**
     * 分配角色
     * @param id
     * @param roleIds
     * @return
     */
    @PostMapping("/assign")
    public ResponseEntity<ResultEnum> assign(Long id,Integer[] roleIds){
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("userId",id);
            map.put("roleIds", roleIds);
            userService.insertUserRoles(map);
            return ResponseEntity.success(ResultEnum.SUCCESS,"权限分配成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.fail(ResultEnum.FAIL, "分配权限异常"+e);
        }
    }

    @PostMapping("/test/{jwtToken}")
    public String testJwtVerify(@PathVariable("jwtToken") String jwtToken){
        JWTVerifyResult verify = JWTUtil.verify(jwtToken);
        return verify.getMessage();
    }
}
