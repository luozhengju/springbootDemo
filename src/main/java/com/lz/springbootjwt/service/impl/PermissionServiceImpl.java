package com.lz.springbootjwt.service.impl;

import com.lz.springbootjwt.mapper.PermissionMapper;
import com.lz.springbootjwt.mapper.RoleMapper;
import com.lz.springbootjwt.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author lzj
 * @create 2019-07-26 18:39
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<String> findPermissionUrl() {

        return permissionMapper.findPermissionUrl();
    }

    @Override
    public List<String> findPermissionUrl(List<Integer> roleIds) {
       //List<Integer> permissionIds = roleMapper.findPermissionIdByRoleId(roleIds);
        return permissionMapper.findRolePermissionUrl(roleIds);
    }

    @Override
    public Set<String> findPermsByUserId(Long userId) {
        return permissionMapper.findPermsByUserId(userId);
    }
}
