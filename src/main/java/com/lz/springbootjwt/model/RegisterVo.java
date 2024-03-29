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

    @ApiModelProperty(value = "登陆账号")
    private String loginAccount;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "用户姓名")
    private String userName;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    //@ApiModelProperty(value = "创建时间")
    //private String createTime;

}
