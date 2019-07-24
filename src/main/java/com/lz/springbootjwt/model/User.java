package com.lz.springbootjwt.model;

import lombok.Data;

/**
 * @author lzj
 * @create 2019-07-22 17:47
 */
@Data
public class User {
    private Long userId;

    private String userName;

    private String password;
}
