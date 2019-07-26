package com.lz.springbootjwt.service;

import com.lz.springbootjwt.model.User;

import java.util.List;
import java.util.Map;

/**
 * @author lzj
 * @create 2019-07-22 18:10
 */
public interface UserService {
    List<User> selectByName(String loginAccount);

    User findUserById(Long id);

    void insertUser(User user);

    void updataUser(User user);

    void deleteUser(String ids);

    void insertUserRoles(Map<String, Object> map);

    List<Integer> findRoleByUserId(Long id);

}
