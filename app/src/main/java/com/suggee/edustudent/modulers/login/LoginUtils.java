package com.suggee.edustudent.modulers.login;

import android.text.TextUtils;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao123@126.com
 * Date:    16/7/26
 * Description:
 */
public class LoginUtils {

    //登陆用户名
    public static boolean nameValid(CharSequence name) {
        return !TextUtils.isEmpty(name) && name.length() > 6;
    }

    //登录手机号
    public static boolean phoneValid(CharSequence phone) {
        return !TextUtils.isEmpty(phone) && phone.length() == 11;
    }

    //登陆密码
    public static boolean pwdValid(CharSequence pwd) {
        return !TextUtils.isEmpty(pwd);
    }

    //验证码合法性
    public static boolean codeValid(CharSequence code) {
        return !TextUtils.isEmpty(code) && code.length() == 4;
    }

}
