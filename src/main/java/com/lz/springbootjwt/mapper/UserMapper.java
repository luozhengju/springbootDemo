package com.lz.springbootjwt.mapper;

import com.lz.springbootjwt.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author lzj
 * @create 2019-07-22 18:11
 */
@Mapper
public interface UserMapper {
    List<User> selectByName(String loginAccount);


    User findUserById(Long id);

    Integer insertUser(User user);

    Integer updataUser(User user);

    /**
     * 软删除用户信息
     * @param id
     * @return
     */
    int deleteUserById(Long id);

    /**
     * 批量软删除用户信息
     * @param idList
     * @return
     */
    int batchDeleteUserByIds(List<Long> idList);

    /**
     * 分配用户角色
     * @param map
     */
    void insertUserRoles(Map<String, Object> map);

    List<Integer> findRoleByUserId(Long id);

}
