package com.lz.springbootjwt.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

/**
 * @author lzj
 * @create 2019-07-26 17:36
 */
@Mapper
public interface RoleMapper {
    void insertRolePermission(HashMap<String, Object> map);

}
