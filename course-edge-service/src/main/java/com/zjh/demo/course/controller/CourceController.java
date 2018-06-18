package com.zjh.demo.course.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zjh.demo.course.dto.CourseDTO;
import com.zjh.demo.course.service.CourseService;
import com.zjh.demo.thrift.user.dto.UserDTO;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhengjianhui on 6/18/18
 */
@RestController
public class CourceController {

    @Reference
    private CourseService courseService;


    @GetMapping("/courseList")
    public List<CourseDTO> courseList(HttpServletRequest request) {
        UserDTO userDTO = (UserDTO) request.getAttribute("user");
        System.out.println(userDTO);

        return courseService.courceList();
    }
}
