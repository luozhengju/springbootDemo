package com.lz.springbootjwt.model;


import lombok.Data;

/**
 * @author lzj
 * @create 2019-07-22 17:05
 */

@Data
public class ResponseEntity<T> {

    //返回码，0为成功，非0为失败
    private int status = ResponseStatus.SUCCESS.getValue();

    //返回描述信息
    private String message = ResponseStatus.SUCCESS.getMessage();

    private T data;

    /**
     * 返回成功
     * @param data
     * @param <T>
     * @return
     */
    public static<T> ResponseEntity<T> success(T data){
        return success(data,null);
    }

    public static<T> ResponseEntity<T> success(T data,String message){
        ResponseEntity<T> resp = new ResponseEntity<>();
        resp.setStatus(ResponseStatus.SUCCESS.getValue());
        resp.setMessage(ResponseStatus.FAIL.getMessage());
        if(null != data){
            resp.setData(data);
        }
        if(null != message){
            resp.setMessage(message);
        }
        return resp;
    }

    public static<T> ResponseEntity<T> fail(T data){
        return fail(data,null);
    }

    public static<T> ResponseEntity<T> fail(T data,String message){
        ResponseEntity<T> resp = new ResponseEntity<>();
        resp.setStatus(ResponseStatus.FAIL.getValue());
        resp.setMessage(message!=null?message:ResponseStatus.FAIL.getMessage());
        resp.setData(data);
        return resp;
    }

}
























