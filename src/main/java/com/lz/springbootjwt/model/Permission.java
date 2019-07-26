package com.lz.springbootjwt.model;

import lombok.Data;

/**
 * @author lzj
 * @create 2019-07-26 18:36
 */
@Data
public class Permission {
    private Integer id;
    private String name;
    private Integer pid;
    private String url;
}
