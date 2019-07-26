package com.lz.springbootjwt.controller;

import com.lz.springbootjwt.model.ResponseEntity;
import com.lz.springbootjwt.model.ResultEnum;
import com.lz.springbootjwt.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author lzj
 * @create 2019-07-26 13:27
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/assign")
    public ResponseEntity<ResultEnum> assignRole(Integer roleId,Integer[] permissionIds){
        try {
            HashMap<String,Object> map = new HashMap<>();
            map.put("roleId", roleId);
            map.put("permissionIds", permissionIds);
            roleService.insertRolePermission(map);
            return ResponseEntity.success(ResultEnum.SUCCESS, "许可分配成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.fail(ResultEnum.FAIL, "许可分配异常"+e);
        }
    }
}
