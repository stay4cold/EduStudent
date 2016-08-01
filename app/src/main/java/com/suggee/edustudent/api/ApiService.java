package com.suggee.edustudent.api;

import com.suggee.edustudent.bean.BaseResponse;
import com.suggee.edustudent.bean.OauthUser;
import com.suggee.edustudent.bean.User;

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

}
