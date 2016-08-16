package com.suggee.edustudent.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao1987@gmail.com
 * Date:    16/8/11
 * Description:
 */
public class TeacherDetail {

    /**
     * avatar :教师头像
     * brief :教师简介
     * is_collect : 是否收藏 0：未收藏 1：已收藏
     * name : 教师名称
     * school : 教师学校
     */

    private String avatar;
    private String brief;
    @SerializedName("is_collect")
    private int collect;
    private String name;
    private String school;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public int getCollect() {
        return collect;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
