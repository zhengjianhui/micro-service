package com.zjh.demo.user.service;

import com.zjh.demo.thrift.user.UserInfo;
import com.zjh.demo.thrift.user.UserService;
import com.zjh.demo.user.mapper.UserMapper;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhengjianhui on 6/17/18
 */
@Service
public class UserServiceImpl implements UserService.Iface {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserInfo getUserById(int id) throws TException {
        UserInfo userInfo = userMapper.getUserById(id);
        return userInfo;
    }

    @Override
    public UserInfo getTeacherById(int id) throws TException {
        return userMapper.getTeacherById(id);
    }

    @Override
    public UserInfo getUserByName(String username) throws TException {
        return userMapper.getUserByUsername(username);
    }

    @Override
    public void regiserUser(UserInfo userInfo) throws TException {
        userMapper.registerUser(userInfo);
    }
}
