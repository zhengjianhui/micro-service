package com.zjh.demo.thrift.user.dto;

/**
 * @author zhengjianhui on 6/18/18
 */
public class TeacherDTO extends UserDTO {

    private String intro;

    private int stars;

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
