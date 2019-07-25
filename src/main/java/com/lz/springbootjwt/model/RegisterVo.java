package com.lz.springbootjwt.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lzj
 * @create 2019-07-25 15:02
 */
@Data
@ApiModel("用户注册Vo")
public class RegisterVo {

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String password;
}
