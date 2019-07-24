package com.lz.springbootjwt.model;

import lombok.Data;

/**
 * @author lzj
 * @create 2019-07-22 17:07
 */
@Data
public class LoginResponse {

    private Long userId;

    private String userName;

    private String jwtToken;
}
