package com.lz.springbootjwt.jwt.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lzj
 * @create 2019-07-23 18:13
 */

public enum  JWTVerifyEnum implements Serializable {

    /**
     * 验证成功
     */
    SUCCESS(200),

    /**
     * 签名错误
     */
    FAIL(801),

    /**
     * 格式错误
     */
    FORMAT_ERROR(803),

    /**
     * 签名过期
     */
    EXPIRED(601);

    private int httpResponseCode;

    private JWTVerifyEnum(int code){
        this.httpResponseCode = code;
    }

    public int getHttpResponseCode(){
        return httpResponseCode;
    }
}
