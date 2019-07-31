package com.lz.springbootjwt.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * @author lzj
 * @create 2019-07-26 18:39
 */
@Mapper
public interface PermissionMapper {

    @Select("select url from t_permission where url is not null and url !=''")
    List<String> findPermissionUrl();

    List<String> findRolePermissionUrl(List<Integer> roleIds);

    Set<String> findPermsByUserId(Long userId);

}
