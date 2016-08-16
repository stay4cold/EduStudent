package com.suggee.edustudent.api;

import com.suggee.edustudent.bean.BaseResponse;
import com.suggee.edustudent.bean.ClassDetailBean;
import com.suggee.edustudent.bean.Classes;
import com.suggee.edustudent.bean.Course;
import com.suggee.edustudent.bean.CourseFilterGrade;
import com.suggee.edustudent.bean.CourseFilterSubject;
import com.suggee.edustudent.bean.OauthUser;
import com.suggee.edustudent.bean.SearchResult;
import com.suggee.edustudent.bean.User;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao123@126.com
 * Date:    16/7/22
 * Description:
 */
public interface ApiService {

    //启动图
    @GET("stu/startup")
    Observable<BaseResponse<User>> getStartUp();

    //注册
    @FormUrlEncoded
    @POST("auth/register")
    Observable<BaseResponse<OauthUser>> register(@Field("mobile") String mobile, @Field("password") String password, @Field("type") String type, @Field("verify_code") String code);

    //登陆 13652118142 111111
    @FormUrlEncoded
    @POST("auth/login")
    Observable<BaseResponse<OauthUser>> login(@Field("user_name") String name, @Field("password") String password, @Field("type") String type);

    //退出登录
    @FormUrlEncoded
    @POST("auth/logout")
    Observable<BaseResponse<String>> logout();

    //修改用户信息
    @FormUrlEncoded
    @PUT("user")
    Observable<BaseResponse<User>> modifyUser(@FieldMap Map<String, String> user);

    //获取用户信息
    @GET("user")
    Observable<BaseResponse<User>> getUser(@Field("type") String type);

    //忘记密码
    @FormUrlEncoded
    @POST("user/forget/password")
    Observable<BaseResponse<String>> forgetPassword(@Field("mobile") String mobile, @Field("password") String password, @Field("type") String type, @Field("verify_code") String code);

    //更新用户头像
    @Multipart
    @POST("user/avatar")
    Observable<BaseResponse<User>> changeAvatar(@Part MultipartBody.Part file);

    //发送注册短信验证码
    @FormUrlEncoded
    @POST("sms/register")
    Observable<BaseResponse<String>> smsRegister(@Field("mobile") String mobile, @Field("type") String type);

    //忘记密码 短信验证码
    @FormUrlEncoded
    @POST("sms/forget")
    Observable<BaseResponse<String>> smsForget(@Field("mobile") String mobile, @Field("type") String type);

    //更改手机号 发送验证码
    @FormUrlEncoded
    @POST("sms/change")
    Observable<BaseResponse<String>> smsChange(@Field("mobile") String mobile, @Field("type") String type);

    //收藏公开课
    @FormUrlEncoded
    @POST("user/collect/ocourse")
    Observable<BaseResponse<String>> collectCourse(@Field("action") String action, @Field("oid") String oid);

    //收藏教师
    @FormUrlEncoded
    @POST("user/collect/teacher")
    Observable<BaseResponse<String>> collectTeacher(@Field("action") String action, @Field("tea_id") String teacherId);

    //收藏班级
    @FormUrlEncoded
    @POST("user/collect/class")
    Observable<BaseResponse<String>> collectClass(@Field("action") String action, @Field("c_id") String classId);

    //获取我的班级列表,注意page是从1开始计算，perpage默认为15
    @GET("stu/class/mine")
    Observable<BaseResponse<List<Classes>>> getClasses(@Query("page") int page, @Query("per_page") int perPage);

    //获取班级详情
    @GET("stu/class/detail")
    Observable<BaseResponse<ClassDetailBean>> getClassDetail(@Query("id") String classId);

    //加入班级
    @FormUrlEncoded
    @POST("stu/class/join")//"cid"和"invest_code"任传一个
    Observable<BaseResponse<String>> joinClass(@FieldMap Map<String, Integer> params);

    //公开课列表
    @GET("stu/ocourse")
    Observable<BaseResponse<List<Course>>> getCourse(@QueryMap Map<String, String> options);

    //获取公开课筛选年级条件
    @GET("grades")
    Observable<BaseResponse<List<CourseFilterGrade>>> getCourseFilterGrade();

    //获取公开课筛选科目条件
    @GET("subjects")
    Observable<BaseResponse<List<CourseFilterSubject>>> getCourseFilterSubject();

    //搜索
    @GET("stu/ocourse/search")
    Observable<BaseResponse<SearchResult>> getSearchResult(@Query("word") String words);

    //获取教师热门公开课
    @GET("stu/teacher/ocourse/hot")
    Observable<String> getCourseHot(@Query("uid") int teaId, @Query("page") int page, @Query("per_page") int perPage);

    //获取教师热门班级
    @GET("stu/teacher/class/hot")
    Observable<String> getClassHot(@Query("uid") int teaId, @Query("page") int page, @Query("per_page") int perPage);

    //获取教师详情
    @GET("stu/teacher/detail")
    Observable<String> getTeaDetail(@Query("uid") int teaId);
}
