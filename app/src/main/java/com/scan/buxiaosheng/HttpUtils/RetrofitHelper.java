package com.scan.buxiaosheng.HttpUtils;




import com.scan.buxiaosheng.ApiService.Api;
import com.scan.buxiaosheng.Common.Constant;
import com.scan.buxiaosheng.Utils.LogUtils;

import java.nio.charset.Charset;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Retrofit请求帮助类
 * Created by BertramTan on 2017/11/25.
 */

public class RetrofitHelper {
    private static final int DEFAULT_TIMEOUT = 30;
    private static OkHttpClient client;
    private static boolean showLog = true;//是否显示打印

    public static <T> T getInstance(Class<T> clazz) {
        if (null == client) {
            client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request request = chain.request();
                        RequestBody requestBody = request.body();
                        if (requestBody != null) {
                            Charset charset = Charset.forName("UTF-8");
                            String paramsStr;
                            Buffer buffer = new Buffer();
                            requestBody.writeTo(buffer);
                            paramsStr = buffer.readString(charset);
                            if (showLog) {
                                LogUtils.e(String.format("%s request params%n%s", request.method(), paramsStr));
                            }
                        }
                        long t1 = System.nanoTime();
                        Response response = chain.proceed(request);
                        long t2 = System.nanoTime();
                        if (showLog) {
                            LogUtils.e(String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
                        }
                        MediaType mediaType = response.body().contentType();
                        String content = response.body().string();
                        if (showLog) {
                            LogUtils.json(content);
                        }
                        return response.newBuilder()
                                .body(ResponseBody.create(mediaType, content))
                                .build();
                    })
                    .connectTimeout(DEFAULT_TIMEOUT + 5, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(false)
                    .build();
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.getBaseUrl(Constant.isDebug))
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(clazz);
    }
}
