package com.zjh.demo.course.filter;

import com.zjh.demo.thrift.user.dto.UserDTO;
import com.zjh.demo.user.client.LoginFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhengjianhui on 6/18/18
 */
public class CourseFilter extends LoginFilter {

    @Override
    protected void login(HttpServletRequest request, HttpServletResponse response, UserDTO userDTO) {
        request.setAttribute("user", userDTO);
    }
}
