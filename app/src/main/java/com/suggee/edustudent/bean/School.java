package com.suggee.edustudent.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao1987@gmail.com
 * Date:    16/8/12
 * Description:
 */
public class School implements Serializable{

    private static final long serialVersionUID = 6243533919873674722L;
    /**
     * area : 津南区
     * city : 天津
     * id : 3497
     * province : 天津
     * school_name : 天津市咸水沽第二中学
     */

    private String area;
    private String city;
    private int id;
    private String province;
    @SerializedName("school_name")
    private String name;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
