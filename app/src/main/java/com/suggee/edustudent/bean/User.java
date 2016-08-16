package com.suggee.edustudent.bean;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao123@126.com
 * Date:    16/7/22
 * Description:
 */
public class User extends RealmObject {

    /**
     * avatar :
     * brief :
     * id : 42
     * level : 高级讲师
     * mobile : 13652118142
     * nick_name :
     * sex : 测试内容8n19
     * sid : 0
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjQyLCJpc3MiOiJodHRwOlwvXC9hcGkueHl6XC9hcGlcL2F1dGhcL2xvZ2luIiwiaWF0IjoxNDY2NjcyMDI5LCJleHAiOjE0NjcyNzY4MjksIm5iZiI6MTQ2NjY3MjAyOSwianRpIjoiOWFiNTA3NDk5Mjk5MzJhODhmNTQxZGZhNmJkMGMwN2QifQ.qUdxsotbVJYQKp_g45I1GiJKF1oyU9ZeGqzkDcHJwd0
     * type : 测试内容6e35
     * user_name : udoF8C5D81556A607CE36E3
     */

    @PrimaryKey
    private int id;

    private String avatar;  //用户头像
    private String brief;   //用户简介
    @SerializedName("grade_id")
    private int gradeId;//年级id
    @SerializedName("grade_name")
    private String gradeName;//年级名称
    private String level;
    private String mobile;

    @SerializedName("nick_name")
    private String nickName;
    private String sex;
    @SerializedName("school_name")
    private String schoolName;//学校名
    private int sid;//学校id
    private String type;    //1->学生 2->老师

    @SerializedName("user_name")
    private String userName;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}
