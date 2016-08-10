package com.suggee.edustudent.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao1987@gmail.com
 * Date:    16/8/4
 * Description:
 */
public class SearchResult {

    private List<Occourse> occourses;

    private List<Teacher> teachers;

    public List<Occourse> getOccourses() {
        return occourses;
    }

    public void setOccourses(List<Occourse> occourses) {
        this.occourses = occourses;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    class Occourse {

        /**
         * avatar : 测试内容vm7e
         * brief : 测试内容w7sw
         * image :
         * pid : 2
         * school : sc
         * teacher_name : udo22654022
         * teahcer_level : 高级讲师
         * title : udo123
         * video_num : 25064
         * view_num : 32720
         */

        private String avatar;
        private String brief;
        private String image;
        private int pid;
        private String school;
        @SerializedName("teacher_name")
        private String name;
        @SerializedName("teacher_level")
        private String level;
        private String title;
        private int video_num;
        private int view_num;

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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getVideo_num() {
            return video_num;
        }

        public void setVideo_num(int video_num) {
            this.video_num = video_num;
        }

        public int getView_num() {
            return view_num;
        }

        public void setView_num(int view_num) {
            this.view_num = view_num;
        }
    }

    class Teacher {

        /**
         * avatar : q
         * id : 35
         * school : udo_school
         * teacher_level : 高级讲师
         * teacher_name : ccess
         */

        private String avatar;
        private int id;
        private String school;
        @SerializedName("teacher_level")
        private String level;
        @SerializedName("teacher_name")
        private String name;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
