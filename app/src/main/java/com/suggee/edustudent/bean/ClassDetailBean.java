package com.suggee.edustudent.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao1987@gmail.com
 * Date:    16/8/5
 * Description:
 */
public class ClassDetailBean {

    /**
     * attention : 用户是否关注该班级    0未关注 1关注
     * brief : ssssss
     * hw_count : 0
     * id : 3
     * logo :
     * mc_count : 0
     * status : 57133
     * stu_num : 1
     * teacher : {"id":48623,"name":"测试内容78q8","status":"测试内容v6y5","type":"测试内容xyp2"}
     * title : fewfew
     * type_name : 1
     */

    private int attention;//用户是否关注该班级    0未关注 1关注
    private String brief;//班级简介
    @SerializedName("hw_name")
    private int homeworkCount;//班级作业数量
    private int id;
    private String logo;
    @SerializedName("mc_count")
    private int courseCount;//班级微课数量
    private int status;//用户是否加入该班级,0等待审核 1已加入 2审核被拒绝 3未加入
    @SerializedName("stu_num")
    private int studentNum;//班级学生数量
    /**
     * id : 48623
     * name : 测试内容78q8
     * status : 测试内容v6y5
     * type : 测试内容xyp2
     */

    private List<TeacherBean> teacher;
    private String title;//班级标题
    @SerializedName("type_name")
    private String typeName;//班级类型名称

    public int getAttention() {
        return attention;
    }

    public void setAttention(int attention) {
        this.attention = attention;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<TeacherBean> getTeacher() {
        return teacher;
    }

    public void setTeacher(List<TeacherBean> teacher) {
        this.teacher = teacher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getHomeworkCount() {
        return homeworkCount;
    }

    public void setHomeworkCount(int homeworkCount) {
        this.homeworkCount = homeworkCount;
    }

    public int getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(int courseCount) {
        this.courseCount = courseCount;
    }

    public int getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(int studentNum) {
        this.studentNum = studentNum;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public static class TeacherBean {
        private int id;
        private String name;
        private String status;//教师加入班级状态,0等待审核 1通过 2拒绝
        private String type;//教师类型,1班主任 0为助教

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
