package com.suggee.edustudent.api;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.suggee.edustudent.bean.OauthUser;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.finalteam.toolsfinal.coder.MD5Coder;
import io.realm.Realm;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author:  wangchenghao
 * Email:   wangchenghao123@126.com
 * Date:    16/7/22
 * Description:
 */
public class ApiClient {

    private Retrofit mRetrofit;
    private ApiService mApiService;
    private OauthUser mOauthUser;

    private ApiClient() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(logInterceptor)
                .addNetworkInterceptor(mTokenInterceptor)
                .addNetworkInterceptor(new StethoInterceptor())
                .build();


        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                                     .setPrettyPrinting()
                                     .serializeNulls()
                                     .create();

        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Api.BASE_URL)
                .build();

        mApiService = mRetrofit.create(ApiService.class);

        mOauthUser = Realm.getDefaultInstance()
                          .where(OauthUser.class)
                          .equalTo("logined", true)
                          .findFirst();
    }

    private static class SingletonHolder {
        private static final ApiClient INSTANCE = new ApiClient();
    }

    public static ApiClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static ApiService getApiService() {
        return getInstance().mApiService;
    }

    Interceptor mTokenInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();

            String random = String.valueOf(System.currentTimeMillis() / 1000);

            // TODO: 16/7/29
            //需要在此动态获取是因为即使使用static来定义token，也有可能被回收，所以需要在回收的时候到DB中
            //获取
            //由于此处所处的线程不确定，而Realm不能跨线程访问，所以每次都需要从Realm中重新取OauthUser,
            //不过速度很快，在几个ms之内，后期可以优化，暂时没有想到更好的办法

            OauthUser user = Realm.getDefaultInstance()
                                  .where(OauthUser.class)
                                  .equalTo("logined", true)
                                  .findFirst();

            Request author = originalRequest.newBuilder()
                                            .header("token", user != null ? user.getToken() : "")
                                            .header("random", random)
                                            .header("sign", MD5Coder.getMD5Code(random + Api.KEY))
                                            .build();

            return chain.proceed(author);
        }
    };

//    class HttpCacheInterceptor implements Interceptor {
//
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request request = chain.request();
//            if (!NetWorkUtil.isNetConnected(App.getAppContext())) {
//                request = request.newBuilder()
//                        .cacheControl(CacheControl.FORCE_CACHE)
//                        .build();
//                Log.d("Okhttp", "no network");
//            }
//
//            Response originalResponse = chain.proceed(request);
//            if (NetWorkUtil.isNetConnected(App.getAppContext())) {
//                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
//                String cacheControl = request.cacheControl().toString();
//                return originalResponse.newBuilder()
//                        .header("Cache-Control", cacheControl)
//                        .removeHeader("Pragma")
//                        .build();
//            } else {
//                return originalResponse.newBuilder()
//                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
//                        .removeHeader("Pragma")
//                        .build();
//            }
//        }
//    }
}
