package com.lz.springbootjwt.jwt.model;

import lombok.Data;

/**
 * @author lzj
 * @create 2019-07-23 14:27
 */
@Data
public class JWTUser {
    private Long userId;
    private String userName;
    private String roles;

}
