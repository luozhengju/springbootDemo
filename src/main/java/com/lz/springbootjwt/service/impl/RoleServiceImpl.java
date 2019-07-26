package com.lz.springbootjwt.service.impl;

import com.lz.springbootjwt.mapper.RoleMapper;
import com.lz.springbootjwt.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author lzj
 * @create 2019-07-26 17:29
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void insertRolePermission(HashMap<String, Object> map) {
        roleMapper.insertRolePermission(map);
    }
}
