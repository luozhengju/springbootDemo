package com.lz.springbootjwt.model;

import lombok.Data;

/**
 * @author lzj
 * @create 2019-07-22 17:47
 */
@Data
public class User {
    private Long id;

    private String userName;

    private String password;

    private String loginAccount;

    private String email;

    private String createTime;
}
