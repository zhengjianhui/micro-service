package com.zjh.demo.course.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.zjh.demo.course.dto.CourseDTO;
import com.zjh.demo.course.mapper.CourseMapper;
import com.zjh.demo.thrift.user.UserInfo;
import com.zjh.demo.thrift.user.dto.TeacherDTO;

import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author zhengjianhui on 6/18/18
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ServiceProvider serviceProvider;

    @Override
    public List<CourseDTO> courceList() {
        List<CourseDTO> courseDTOS = courseMapper.listCourse();
        if (courseDTOS != null) {
            for (CourseDTO dto : courseDTOS) {
                Integer teacherId = courseMapper.getCourseTeacher(dto.getId());
                if (teacherId != null) {
                    try {
                        UserInfo userInfo = serviceProvider.getUserService().getTeacherById(teacherId);
                        TeacherDTO teacherDTO = new TeacherDTO();
                        BeanUtils.copyProperties(userInfo, teacherDTO);
                        dto.setTeacher(teacherDTO);
                    } catch (TException e) {
                        e.printStackTrace();
                    }

                }

            }
        }
        return courseDTOS;
    }
}
