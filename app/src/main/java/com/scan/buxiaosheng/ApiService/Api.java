package com.scan.buxiaosheng.ApiService;

/**
 * 获取APi地址
 * Create By BertramTan on 2018/3/29
 */
public class Api {
    //api基础地址
    public static String getBaseUrl(boolean isDebug){
        if (isDebug)
//            return "http://192.168.3.108:8080/";
            return "https://test.buxiaosheng.com/web-api/";//6  14   http://v22628w782.iok.la/web-api/
        else
            return "https://www.buxiaosheng.com/web-api/";
    }

    //打印页面基础地址
    public static String getPrintUrl(boolean isDebug){
        if (isDebug){
            return "https://test.buxiaosheng.com/dist/#/banknote";
//            return "http://192.168.3.234:8080/#/banknote";
        }else{
//            return "http://www.buxiaosheng.com/web-h5/html/print/";//旧版本正式环境打印
            return "https://www.buxiaosheng.com/dist/#/banknote";//正式环境打印
//            return "http://192.168.3.190:8080/#/banknote";//本地打印调试
        }
    }
}