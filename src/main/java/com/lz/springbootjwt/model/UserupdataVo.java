package com.lz.springbootjwt.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lzj
 * @create 2019-07-25 19:12
 */
@Data
@ApiModel("用户更新Vo类")
public class UserupdataVo {

    private Long id;

    @ApiModelProperty(value = "用户姓名")
    private String userName;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "用户邮箱")
    private String email;
}
