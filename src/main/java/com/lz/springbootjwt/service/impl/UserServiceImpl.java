package com.lz.springbootjwt.service.impl;

import com.lz.springbootjwt.mapper.UserMapper;
import com.lz.springbootjwt.model.RegisterVo;
import com.lz.springbootjwt.model.User;
import com.lz.springbootjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    @Override
    public void deleteUser(String ids) {
        int result = 0;
        String[] splitId = ids.split(",");
        if(splitId.length>1){
            List<Long> idList = string2long(splitId);
            result = userMapper.batchDeleteUserByIds(idList);
        }else {
            result = userMapper.deleteUserById(Long.parseLong(ids));
        }
        if(result<1){
            throw new RuntimeException("用户信息删除失败");
        }

    }

    @Override
    public void insertUserRoles(Map<String, Object> map) {
        userMapper.insertUserRoles(map);
    }

    @Override
    public List<Integer> findRoleByUserId(Long id) {
        return userMapper.findRoleByUserId(id);
    }

    private List<Long> string2long(String[] strs){
        ArrayList<Long> longs = new ArrayList<>();
        for(int i=0;i<strs.length;i++){
            longs.add(Long.parseLong(strs[i]));
        }
        return longs;
    }
}
