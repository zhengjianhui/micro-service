package com.zjh.demo.course.mapper;

import com.zjh.demo.course.dto.CourseDTO;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhengjianhui on 6/18/18
 */
@Repository
public interface CourseMapper {

    @Select("select * from pe_course")
    List<CourseDTO> listCourse();

    @Select("select user_id from pr_user_course where course_id = #{courseId}")
    Integer getCourseTeacher(@Param("courseId") int courseId);
}
