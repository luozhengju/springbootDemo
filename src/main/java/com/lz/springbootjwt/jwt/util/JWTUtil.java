package com.lz.springbootjwt.jwt.util;



import com.lz.springbootjwt.jwt.model.JWTUser;
import com.lz.springbootjwt.jwt.model.JWTVerifyResult;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;


import com.nimbusds.jose.crypto.MACVerifier;
import net.minidev.json.JSONObject;
import org.apache.commons.codec.binary.Base64;

import java.text.ParseException;
import java.util.Arrays;


/**
 * @author lzj
 * @create 2019-07-23 13:36
 */
public  class JWTUtil {
    private final static Base64 BASE_64= new Base64();

    private static String USER_ID = "_user_id";

    private static String USER_NAME = "_user_name";

    private static String USER_ROLES = "_user_roles";
    private static String KEY_EXP = "exp";
    private static String KEY = "yizhidafeigou000000000000000000000000000000000000000000000000000000";




    /**
     * 签发jwt token
     * @param jwtUser
     * @param liveTime
     * @return
     */
    public static String sign(JWTUser jwtUser, long liveTime){
        JSONObject json = new JSONObject();
        json.put(USER_ID, jwtUser.getUserId());
        json.put(USER_NAME, jwtUser.getUserName());
        json.put(USER_ROLES, jwtUser.getRoles());
        if(liveTime>0){
            json.put(KEY_EXP,System.currentTimeMillis()+liveTime );
        }


        byte[] bytesKey = getByteKey();
        //bytesKey = base64Key.decode();
        System.out.println(bytesKey.length);
        //设置jwt头部
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        Payload payload = new Payload(json);
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(bytesKey));
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        String jwt = jwsObject.serialize();
        return jwt;
    }

    /**
     * 验证jwt
     * @param jwtToken
     * @param  //是否需要验证过期
     * @return
     */
    public static JWTVerifyResult verify(String jwtToken){
        if(null != jwtToken){
            try {
                JWSObject jwsObject = JWSObject.parse(jwtToken);
                if(jwsObject == null){
                    return JWTVerifyResult.fail();
                }
                Payload payload = jwsObject.getPayload();
                if(payload == null){
                    return JWTVerifyResult.fail();
                }
                JSONObject json = payload.toJSONObject();
                //验证时间有效性
                if(null != json&&json.containsKey(KEY_EXP)){
                    if(json.getAsNumber(KEY_EXP).longValue()<System.currentTimeMillis()){
                        return JWTVerifyResult.exp();
                    }
                }
                byte[] byteKey = getByteKey();

                JWSVerifier verifier = new MACVerifier(byteKey);
                boolean verify = jwsObject.verify(verifier);

                if(verify){
                    JWTVerifyResult result = JWTVerifyResult.success();
                    result.setPayload(json);
                    return result;
                }else {
                    return JWTVerifyResult.fail();
                }

            } catch (JOSEException e){
                e.printStackTrace();
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return JWTVerifyResult.fail();
    }

    private static byte[] getByteKey(){
        //对私钥加密
        byte[] bytesKey = new byte[32];
        //Base64 base64Key = new Base64(KEY);
        bytesKey = BASE_64.decode(KEY);
        if(bytesKey.length<32){
            bytesKey = Arrays.copyOf(bytesKey, 32);
        }
        return bytesKey;
    }
}
