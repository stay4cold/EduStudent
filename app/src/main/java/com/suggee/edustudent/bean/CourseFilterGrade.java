package com.suggee.edustudent.bean;

import java.io.Serializable;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao1987@gmail.com
 * Date:    16/8/4
 * Description:
 */
public class CourseFilterGrade implements Serializable {
    private static final long serialVersionUID = 4287378307011094491L;
    private String id;//年级id
    private String name;//年级名称

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
