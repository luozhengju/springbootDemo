package com.lz.springbootjwt.jwt.model;

import lombok.Data;
import net.minidev.json.JSONObject;

/**
 * @author lzj
 * @create 2019-07-23 18:26
 */
@Data
public class JWTVerifyResult {
    //private int code;
    private String message;
    private JSONObject payload;
    private JWTVerifyEnum jwtVerifyEnum;

    public static JWTVerifyResult fail(){
        return fail("验证失败");
    }

    private static JWTVerifyResult fail(String msg) {
        JWTVerifyResult result = new JWTVerifyResult();
        //result.setCode(0);
        result.setJwtVerifyEnum(JWTVerifyEnum.FAIL);
        result.setMessage(msg);
        return result;
    }

    public static JWTVerifyResult exp(){
        JWTVerifyResult result = new JWTVerifyResult();
        //result.setCode();
        result.setJwtVerifyEnum(JWTVerifyEnum.EXPIRED);
        result.setMessage("JWT超时");
        return result;
    }

    public static JWTVerifyResult success(){
        JWTVerifyResult result = new JWTVerifyResult();
        result.setJwtVerifyEnum(JWTVerifyEnum.SUCCESS);
        result.setMessage("验证成功");
        return result;
    }
}

















