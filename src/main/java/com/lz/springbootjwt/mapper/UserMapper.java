package com.lz.springbootjwt.mapper;

import com.lz.springbootjwt.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author lzj
 * @create 2019-07-22 18:11
 */
@Mapper
public interface UserMapper {
    List<User> selectByName(String userName);

}
