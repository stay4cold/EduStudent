package com.suggee.edustudent.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao1987@gmail.com
 * Date:    16/8/2
 * Description:
 */
public class Classes {

    /**
     * id : 1
     * logo :
     * status : 用户是否加入该班级,0审核未通过 1审核通过
     * stu_num : 20
     * title : 测试一般
     * type : 1为c2c班级 2为c2b班级
     * type_name : c2c或者学校名称
     * unRead : 是否有未读信息,0没有 1有
     */

    private int id;
    private String logo;
    private String status;
    @SerializedName("stu_num")
    private int num;
    private String title;
    private String type;
    @SerializedName("type_name")
    private String typeName;
    private int unRead;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getUnRead() {
        return unRead;
    }

    public void setUnRead(int unRead) {
        this.unRead = unRead;
    }
}
