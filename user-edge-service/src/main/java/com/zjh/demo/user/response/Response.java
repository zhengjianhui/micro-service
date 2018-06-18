package com.zjh.demo.user.response;

import java.io.Serializable;

/**
 * @author zhengjianhui on 6/17/18
 */
public class Response implements Serializable {

    private String code;

    private String message;

    public Response(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static Response responseFor1001() {
        return new Response("1001", "username or password is error");
    }

    public static Response responseFor1002() {
        return new Response("1002", "username or password is error");
    }
}
