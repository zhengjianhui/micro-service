package com.zjh.demo.user.controller;

import com.zjh.demo.thrift.user.UserInfo;
import com.zjh.demo.thrift.user.dto.UserDTO;
import com.zjh.demo.user.redis.RedisClient;
import com.zjh.demo.user.response.LoginResponse;
import com.zjh.demo.user.response.Response;
import com.zjh.demo.user.thrift.ServiceProvider;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author zhengjianhui on 6/17/18
 */
@RestController
public class UserController {

    @Autowired
    private ServiceProvider serviceProvider;

    @Autowired
    private RedisClient redisClient;

    @PostMapping(value = "/login")
    public Response login(@RequestParam("username") String username,
                      @RequestParam("password") String password) {

        // 1. 验证用户名密码
        UserInfo userInfo = null;
        try {
            userInfo = serviceProvider.getUserService().getUserByName(username);
        } catch (TException e) {
            // TODO: 6/17/18 log
        }

        if(userInfo == null) {
            return Response.responseFor1001();
        }

        if(password == null || !password.equals(userInfo.getPassword())) {
            return Response.responseFor1001();
        }

        // 2. 生成 token
        String token = genToken();
        // 3. 缓存用户
        redisClient.set(token, toDTO(userInfo), 3600);

        return new LoginResponse("200", "success", token);

    }

    @PostMapping(value = "/register")
    public Response register(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("mobile") String mobile,
                             @RequestParam("email") String email,
                             @RequestParam("verifyCode") String verifyCode) {

        // 1. 验证用户名密码

        try {
//            serviceProvider.getMessageService().sendEmailMessage(email, "123");
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(username);
            userInfo.setPassword(password);
            userInfo.setEmail(email);
            userInfo.setMobile(mobile);
            userInfo.setRealName(verifyCode);
            serviceProvider.getUserService().regiserUser(userInfo);
        } catch (TException e) {
            // TODO: 6/17/18 log
            System.out.println(e);
        }

        return new Response("200", "SUCCESS");

    }

    @GetMapping(value = "/authentication")
    public UserDTO authentication(@RequestHeader String token) {
        return redisClient.get(token);
    }

    private UserDTO toDTO(UserInfo userInfo) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userInfo.getId());
        userDTO.setEmail(userInfo.getEmail());
        userDTO.setMobile(userInfo.getMobile());
        userDTO.setPassword(userInfo.getPassword());
        userDTO.setUsername(userInfo.getUsername());
        userDTO.setRealName(userInfo.getRealName());
        return userDTO;
    }

    private String genToken() {
        return randomCode("0123456789", 32);
    }

    private String randomCode(String s, int size) {
        StringBuilder sb = new StringBuilder(size);
        Random random = new Random();
        for(int i = 0; i < size; i++) {
            int loc = random.nextInt(s.length());
            sb.append(s.charAt(loc));
        }
        return sb.toString();
    }
}
