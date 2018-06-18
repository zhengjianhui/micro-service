package com.zjh.demo.user.response;

/**
 * @author zhengjianhui on 6/17/18
 */
public class LoginResponse extends Response {

    private String token;

    public LoginResponse(String code, String message, String token) {
        super(code, message);
        this.token = token;
    }

    public LoginResponse(String code, String message) {
        super(code, message);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
