package com.suggee.edustudent.common;

import com.suggee.edustudent.bean.OauthUser;
import com.suggee.edustudent.bean.User;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao123@126.com
 * Date:    16/7/22
 * Description:
 */
public final class AppContext {

    private static OauthUser sOauthUser;

    /**
     * The constructor is private to enforce the use of the static one.
     */
    private AppContext() {

    }

    public static void setOauthUser(OauthUser oauthUser) {
        sOauthUser = oauthUser;
    }

    public static OauthUser getOauthUser() {
        return sOauthUser;
    }

    public static User getUser() {
        if (getOauthUser() == null) {
            return null;
        }

        return getOauthUser().getUser();
    }

    public static int getUserId() {
        if (getOauthUser() == null) {
            return 0;
        }

        return getOauthUser().getId();
    }

    public static String getUserToken() {
        if (getOauthUser() == null) {
            return null;
        }

        return getOauthUser().getToken();
    }
}
