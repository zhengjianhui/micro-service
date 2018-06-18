package com.zjh.demo.user.mapper;

import com.zjh.demo.thrift.user.UserInfo;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author zhengjianhui on 6/17/18
 */
@Repository
public interface UserMapper {

    @Select("select * from pe_user where id = #{id}")
    UserInfo getUserById(@Param("id") Integer id);


    @Select("select * from pe_user where username = #{username}")
    UserInfo getUserByUsername(@Param("username") String username);

    @Insert("insert into pe_user (username, password, real_name, mobile, email)" +
            "values(#{u.username},#{u.password},#{u.realName},#{u.mobile},#{u.email})")
    void registerUser(@Param("u") UserInfo userInfo);

    @Select("select u.id,u.username,u.password,u.real_name as realName," +
            "u.mobile,u.email,t.intro,t.stars from pe_user u," +
            "pe_teacher t where u.id=#{id} " +
            "and u.id=t.user_id")
    UserInfo getTeacherById(@Param("id") Integer id);



}
