package com.suggee.edustudent.modulers.course;

import com.suggee.edustudent.common.AppConfig;

import java.util.HashMap;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao1987@gmail.com
 * Date:    16/8/4
 * Description:
 * 公开课筛选服务器请求参数封装
 */
public class CourseRequestFilter extends HashMap<String, String> {
    private static final long serialVersionUID = -6445593038805902740L;
    private String areaId;//区域id
    private String gradeId;//年级id
    private String order;//desc 降序 asc 升序 默认 desc
    private String page;
    private String perPpge;
    private String sort;//up 热度最高 view_num 观看最多 created_at 创建时间 默认 id 数据库顺序
    private String subjectId;//科目id

    public CourseRequestFilter() {
        setPerPpge(String.valueOf(AppConfig.PER_PAGE));
    }

    public String getAreaId() {
        return get("area_id");
    }

    public void setAreaId(String areaId) {
        put("area_id", areaId);
    }

    public String getGradeId() {
        return get("grade_id");
    }

    public void setGradeId(String gradeId) {
        put("grade_id", gradeId);
    }

    public String getOrder() {
        return get("order");
    }

    public void setOrder(String order) {
        put("order", order);
    }

    public String getPage() {
        return get("page");
    }

    public void setPage(String page) {
        put("page", page);
    }

    public String getPerPpge() {
        return get("per_page");
    }

    public void setPerPpge(String perPpge) {
        put("per_page", perPpge);
    }

    public String getSort() {
        return get("sort");
    }

    public void setSort(String sort) {
        put("sort", sort);
    }

    public String getSubjectId() {
        return get("subject_id");
    }

    public void setSubjectId(String subjectId) {
        put("subject_id", subjectId);
    }
}
