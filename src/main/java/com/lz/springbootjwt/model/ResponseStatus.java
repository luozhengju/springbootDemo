package com.lz.springbootjwt.model;

/**
 * @author lzj
 * @create 2019-07-22 17:31
 */
public enum  ResponseStatus {

    SUCCESS(0,"调用成功"),
    FAIL(1,"调用失败"),

    NO_AUTH(403,"无权限操作"),

    EMPTY(0,"无返回值"),

    NOTEMPTY(1,"有返回值"),
    SESSION_OVERTIME(601,"session超时");

    private int value;
    private String message;

    private ResponseStatus(int value,String message){
        this.value=value;
        this.message=message;
    }

    public int getValue(){
        return value;
    }

    public String getMessage(){
        return message;
    }
}
