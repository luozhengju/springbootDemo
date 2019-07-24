package com.lz.springbootjwt.service.impl;

import com.lz.springbootjwt.mapper.UserMapper;
import com.lz.springbootjwt.model.User;
import com.lz.springbootjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lzj
 * @create 2019-07-22 18:11
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectByName(String userName) {
        return userMapper.selectByName(userName);
    }
}
