package com.scan.buxiaosheng.HttpUtils;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.scan.buxiaosheng.Common.Constant;
import com.scan.buxiaosheng.Utils.GsonUtils;
import com.scan.buxiaosheng.Utils.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Okhttp3封装
 * 使用之前请先确认含有okhttp3的jar包或者依赖
 * 在build.gradle文件中的dependencies添加下面这句话即可
 * compile 'com.squareup.okhttp3:okhttp:3.8.1'
 * Created by Bertram on 2017/8/30.
 */

public class OkHttpHelper {
    private OkHttpClient client;
    public static final int DOWNLOAD_FAIL = 0;
    public static final int DOWNLOAD_PROGRESS = 1;
    public static final int DOWNLOAD_SUCCESS = 2;
    private static Handler handler = new Handler();
    private static String result = "";
    private static OkHttpHelper self = new OkHttpHelper();

    private OkHttpHelper() {
        client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    public static OkHttpHelper getInstance() {
        return self;
    }

    /**
     * 不带参数的OkHttp Get请求
     *
     * @param url      请求地址
     * @param listener 请求结果监听
     */
    public void HttpGet(String url, final onHttpListener listener) {
        HttpGet(url, null, false, listener, false);
    }

    /**
     * 不带参数的OkHttp Get请求
     *
     * @param url      请求地址
     * @param listener 请求结果监听
     */
    public void HttpGet(String url, final onHttpListener listener, boolean needHeader) {
        HttpGet(url, null, false, listener, needHeader);
    }

    /**
     * 带参数
     * OkHttp Get请求 不需要解密
     *
     * @param url      请求地址
     * @param params   拼接的参数
     * @param listener 请求结果监听
     */
    public void HttpGet(String url, List<KeyValue> params, final onHttpListener listener) {
        HttpGet(url, params, false, listener, false);
    }

    /**
     * 带参数
     * OkHttp Get请求 不需要解密
     *
     * @param url      请求地址
     * @param params   拼接的参数
     * @param listener 请求结果监听
     */
    public void HttpGet(String url, List<KeyValue> params, final onHttpListener listener, boolean needHeader) {
        HttpGet(url, params, false, listener, needHeader);
    }

    /**
     * 不带参数
     * OkHttp Get请求
     *
     * @param url         请求地址
     * @param listener    请求结果监听
     * @param needdecript 是否需要解密
     */
    public void HttpGet(String url, boolean needdecript, final onHttpListener listener, boolean needHeader) {
        HttpGet(url, null, needdecript, listener, needHeader);
    }

    /**
     * OkHttp Get请求
     *
     * @param url         请求地址
     * @param listener    请求结果监听
     * @param params      拼接的参数
     * @param needdecript 是否需要解密
     */
    public void HttpGet(String url, List<KeyValue> params, final boolean needdecript, final onHttpListener listener, boolean needHeader) {
        listener.onStart();
        result = "";
        String param = parse2Params(params);
        if (!TextUtils.isEmpty(param)) {
            url = url + "?" + parse2Params(params, needHeader);
        }
        Request request = new Request.Builder()
                .url(url)
                .build();
        handler.post(() -> {
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    handler.post(() -> {
                        listener.onFailure("访问失败");
                        listener.onFinish();
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    if (!needdecript) {
                        result = response.body().string();
                    } else {
                        //解密返回的数据(此处替换处理的解密方法)
                        result = response.body().string();
                    }
                    handler.post(() -> {
                        listener.onSuccess(result);
                        listener.onFinish();
                    });
                }
            });
        });
    }


    /**
     * 不需要监听的Post请求
     *
     * @param url
     * @param params
     */
    public void HttpPost(String url, Object params) {
        HttpPost(url, params, false, null);
    }

    /**
     * OkHttp Post请求  不需要解密
     *
     * @param url      请求地址
     * @param listener 请求结果监听
     * @param params   请求参数
     */
    public void HttpPost(String url, Object params, final onHttpListener listener) {
        HttpPost(url, params, false, listener);
    }

    /**
     * OkHttp Post请求 不需要解密
     *
     * @param url      请求地址
     * @param listener 请求结果监听
     * @param params   请求参数
     */
    public void HttpPost(String url, List<KeyValue> params, final onHttpListener listener) {
        HttpPost(url, params, false, listener);
    }


    /**
     * OkHttp Post请求 是否需要解密
     *
     * @param url         请求地址
     * @param listener    请求结果监听
     * @param params      请求参数
     * @param needdecript 是否需要解密
     */
    public void HttpPost(String url, List<KeyValue> params, boolean needdecript, final onHttpListener listener) {
        HttpPost(url, params, needdecript, listener);
    }


    /**
     * 基础OkHttp Post 请求
     *
     * @param url         请求地址
     * @param listener    请求结果监听
     * @param params      请求需要的参数列表
     * @param needdecript 是否需要解密
     */
    public void HttpPost(String url, Object params, final boolean needdecript, final onHttpListener listener) {
        if (listener != null) {
            listener.onStart();
        }
        result = "";
        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        RequestBody requestBody = null;
        if (null != params) {
            String request = GsonUtils.toJson(params);
            if (Constant.isDebug) {
                LogUtils.e("params:" + request);
            }
            requestBody = RequestBody.create(mediaType, request);
        } else {
            if (listener != null) {
                listener.onFailure("参数为空");
                listener.onFinish();
            }
            return;
        }
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        handler.post(() -> {
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (listener != null) {
                        listener.onFailure("访问失败");
                        listener.onFinish();
                    }
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    if (!needdecript) {
                        result = response.body().string();
                    } else {
                        //解密返回的数据(此处替换处理的解密方法)
                        result = response.body().string();
                    }
                    if (listener != null) {
                        listener.onSuccess(result);
                        listener.onFinish();
                    }
                }
            });
        });
    }

    /**
     * 基础 Http Put请求
     *
     * @param url      url
     * @param params   参数
     * @param listener 回调
     */
    public void HttpPut(String url, Object params, final onHttpListener listener) {
        if (listener != null) {
            listener.onStart();
        }
        result = "";
        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        RequestBody requestBody = null;
        if (null != params) {
            String request = GsonUtils.toJson(params);
            if (Constant.isDebug) {
                LogUtils.e("params:" + request);
            }
            requestBody = RequestBody.create(mediaType, request);
        } else {
            if (listener != null) {
                listener.onFailure("参数为空");
                listener.onFinish();
            }
            return;
        }
        Request request = new Request.Builder()
                .url(url)
                .put(requestBody)
                .build();
        handler.post(() -> {
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (listener != null) {
                        listener.onFailure("访问失败");
                        listener.onFinish();
                    }
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    result = response.body().string();
                    if (listener != null) {
                        listener.onSuccess(result);
                        listener.onFinish();
                    }
                }
            });
        });
    }

    /**
     * 网络请求监听接口
     */
    public interface onHttpListener {
        /**
         * 请求开始之前
         */
        void onStart();

        /**
         * 请求成功
         *
         * @param result 返回的实体类
         */
        void onSuccess(String result);

        /**
         * 请求失败
         *
         * @param error 失败信息
         */
        void onFailure(String error);

        /**
         * 请求结束
         */
        void onFinish();
    }

    public static class KeyValue {
        private String name;
        private Object value;

        public KeyValue(String key, String value) {
            this.name = key;
            this.value = value;
        }

        public KeyValue(String key, boolean value) {
            this.name = key;
            this.value = value;
        }

        public KeyValue(String key, int value) {
            this.name = key;
            this.value = value;
        }

        public KeyValue(String key, File value) {
            this.name = key;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }

    /**
     * 拼接请求参数 json参数格式
     *
     * @param params 动态参数list
     * @return 拼接参数字符串
     */
    private static String parse2jsonParams(List<KeyValue> params) {
        return parse2jsonParams(params, false);
    }

    /**
     * 拼接请求参数 json参数格式
     *
     * @param params           动态参数list
     * @param needHeaderParams 是否需要添加固定请求参数
     * @return 拼接参数字符串
     */
    private static String parse2jsonParams(List<KeyValue> params, boolean needHeaderParams) {
        StringBuilder param = new StringBuilder();
        if (needHeaderParams) {
            params.addAll(headerParams());
        }
        if (params != null && params.size() != 0) {
            param.append("{");
            for (int i = 0; i < params.size(); i++) {
                param.append("\"" + params.get(i).getName() + "\":\"" + params.get(i).getValue() + "\"");
                param.append(",");
            }
            param.deleteCharAt(param.toString().length() - 1);
            param.append("}");
            Log.e("params2json-------->", param.toString());
        }
        return param.toString();
    }

    /**
     * 拼接请求参数 get请求参数格式
     *
     * @param params 动态参数list
     * @return 拼接参数字符串
     */
    private static String parse2Params(List<KeyValue> params) {
        return parse2Params(params, false);
    }

    /**
     * 拼接请求参数 正常参数格式
     *
     * @param params           动态参数list
     * @param needHeaderParams 是否需要添加固定请求参数
     * @return 拼接参数字符串
     */
    private static String parse2Params(List<KeyValue> params, boolean needHeaderParams) {
        StringBuilder param = new StringBuilder();
        if (needHeaderParams) {
            params.addAll(headerParams());
        }
        if (params != null && params.size() != 0) {
            for (int i = 0; i < params.size(); i++) {
                param.append(params.get(i).getName() + "=" + params.get(i).getValue());
                param.append("&");
            }
            param.deleteCharAt(param.toString().length() - 1);
            Log.e("params2url--------->", param.toString());
        }
        return param.toString();
    }

    /**
     * 添加固定请求参数头
     *
     * @return 固定请求参数list
     */
    private static List<KeyValue> headerParams() {
        List<KeyValue> params = new ArrayList<>();
        return params;
    }

    public void download(final String url, final String savePath, final OnDownloadListener listener) {
        this.listener = listener;
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = Message.obtain();
                message.what = DOWNLOAD_FAIL;
                mHandler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Message message = Message.obtain();
                    message.what = DOWNLOAD_FAIL;
                    mHandler.sendMessage(message);
                    return;
                }
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                //储存下载文件的目录
                String saveDir = isExistDir(savePath);
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(saveDir, getNameFromUrl(url));
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        //下载中
                        Message message = Message.obtain();
                        message.what = DOWNLOAD_PROGRESS;
                        message.obj = progress;
                        mHandler.sendMessage(message);
                    }
                    fos.flush();
                    //下载完成
                    Message message = Message.obtain();
                    message.what = DOWNLOAD_SUCCESS;
                    message.obj = file.getAbsolutePath();
                    mHandler.sendMessage(message);
                } catch (Exception e) {
                    Message message = Message.obtain();
                    message.what = DOWNLOAD_FAIL;
                    mHandler.sendMessage(message);
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {

                    }
                    try {
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {

                    }
                }

            }
        });
    }

    //从Url中获取文件名
    private String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    //是否已经存在
    private String isExistDir(String saveDir) throws IOException {
        File downloadFile = new File(saveDir);
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }

    //下载handler处理器
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DOWNLOAD_PROGRESS:
                    listener.onDownloading((Integer) msg.obj);
                    break;
                case DOWNLOAD_FAIL:
                    listener.onDownloadFailed();
                    break;
                case DOWNLOAD_SUCCESS:
                    listener.onDownloadSuccess((String) msg.obj);
                    break;
            }
        }
    };

    //下载监听
    OnDownloadListener listener;

    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess(String path);

        /**
         * 下载进度
         *
         * @param progress
         */
        void onDownloading(int progress);

        /**
         * 下载失败
         */
        void onDownloadFailed();
    }
}
