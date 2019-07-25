package com.lz.springbootjwt.service.impl;

import com.lz.springbootjwt.mapper.UserMapper;
import com.lz.springbootjwt.model.RegisterVo;
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
    public List<User> selectByName(String loginAccount) {
        return userMapper.selectByName(loginAccount);
    }

    @Override
    public User findUserById(Long id) {
        return userMapper.findUserById(id);
    }

    @Override
    public void insertUser(User user) {
        Integer result = userMapper.insertUser(user);
        if(result == null){
            throw new RuntimeException("注册失败");
        }

    }

    @Override
    public void updataUser(User user) {
       Integer result = userMapper.updataUser(user);
       if(result == null){
           throw new RuntimeException("用户更新失败");
       }
    }
}
