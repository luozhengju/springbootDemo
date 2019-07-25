package com.lz.springbootjwt.service;

import com.lz.springbootjwt.model.RegisterVo;
import com.lz.springbootjwt.model.User;

import java.util.List;

/**
 * @author lzj
 * @create 2019-07-22 18:10
 */
public interface UserService {
    List<User> selectByName(String userName);

    User findUserById(Integer userId);

    void insertUser(RegisterVo registerVo);

}
