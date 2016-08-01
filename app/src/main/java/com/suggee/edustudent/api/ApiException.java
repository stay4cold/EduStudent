package com.suggee.edustudent.api;

import com.suggee.edustudent.bean.BaseResponse;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao1987@gmail.com
 * Date:    16/7/28
 * Description:
 *
 * 服务器返回的一些错误code，便于统一处理
 */
public class ApiException extends RuntimeException{
    private static final long serialVersionUID = 8046593716505042492L;

    public static final int MISS_PARAMETERS = 1001;//缺少访问参数
    public static final int DATA_SUCCESS = 2000;//成功访问并返回数据
    public static final int DATA_EMPTY_SUCCESS = 2001;//成功返回空数据
    public static final int TIME_OUT = 910;//请求超时
    public static final int DATABASE_CONNECT_TIMEOUT = 911;//数据库连接失败
    public static final int RESOURCE_PERMISSION = 920;//资源权限错误
    public static final int FOLDER_PERMISSION = 921;//目录权限不足
    public static final int FILE_PERMISSION = 922;//文件权限不足

    private int code;
    private String msg;

    public ApiException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ApiException(BaseResponse response) {
        this.code = response.getCode();
        this.msg = response.getMsg();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
