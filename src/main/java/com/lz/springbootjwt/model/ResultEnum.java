package com.lz.springbootjwt.model;

/**
 * @author lzj
 * @create 2019-07-26 13:31
 */
public enum ResultEnum {

    SUCCESS(1,true),

    FAIL(0,false);

    private int code;

    private Boolean status;

    private ResultEnum(int code,Boolean status){
        this.code = code;
        this.status = status;
    }

    public int getCode(){
        return code;
    }

    public Boolean getStatus(){
        return status;
    }
}
