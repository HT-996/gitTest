package com.scan.buxiaosheng.Helper;

import android.content.Context;
import android.text.TextUtils;

import com.scan.buxiaosheng.Base.BaseEntity;
import com.scan.buxiaosheng.Entity.UserEntity;
import com.scan.buxiaosheng.Utils.GsonUtils;
import com.scan.buxiaosheng.Utils.SharePreferenceUtils;

/**
 * 用户帮助类
 * Created by BertramTan on 2018/5/10
 */
public class UserHelper {
    private static UserEntity entity;
    private final String className = getClass().getSimpleName();
    private final String user = "USER";
    private final String token = "USER_TOKEN";
    private final String userId = "USER_ID";
    private final String userType = "USER_TYPE";
    private final String guide = "guide";
    private final String companyId = "COMPANYID_ID";
    private final String companyName = "COMPANYID_NAME";
    private final String business = "BUSINESS";
    private final String registration = "REGISTRATION";
    private final String firstGuideHome = "FIRSTGUIDEHOME";
    private final String firstGuideSetting = "FIRSTGUIDESETTING";
    private final String exUserPhone = "EXUSERPHONE";
    private static UserHelper instance = new UserHelper();

    public static UserHelper getInstance(){
        return instance;
    }

    /**
     * 保存设备id
     */
    public void setRegistration(Context context, String id){
        SharePreferenceUtils.getDefault(context,className).put(registration,id);
    }

    /**
     * 获取极光设备注册id
     * @return 设备id
     */
    public String getRegistration(Context context){
        return SharePreferenceUtils.getDefault(context,className).get(registration,"");
    }

    /**
     * 保存数据到本地
     * @param userEntity 用户实体
     */
    public void saveData(Context context, BaseEntity<UserEntity> userEntity){
        entity = userEntity.getData();
        String data = GsonUtils.toJson(userEntity.getData());
        SharePreferenceUtils.getDefault(context,className).put(user,data);
        SharePreferenceUtils.getDefault(context,className).put(token,userEntity.getData().getToken());
        SharePreferenceUtils.getDefault(context,className).put(userId,userEntity.getData().getUserId());
        SharePreferenceUtils.getDefault(context,className).put(userType,userEntity.getData().getType());
        SharePreferenceUtils.getDefault(context,className).put(companyId,userEntity.getData().getCompanyId());
        SharePreferenceUtils.getDefault(context,className).put(companyName,userEntity.getData().getCompanyName());
        SharePreferenceUtils.getDefault(context,className).put(business,userEntity.getData().getBusinessId());
    }

    /**
     * 清楚本地缓存
     */
    public void clearData(Context context){
        entity = null;
        SharePreferenceUtils.getDefault(context,className).put(user,"");
        SharePreferenceUtils.getDefault(context,className).put(token,"");
        SharePreferenceUtils.getDefault(context,className).put(userId,(long)0);
        SharePreferenceUtils.getDefault(context,className).put(userType,0);
    }

    /**
     * 保存体验用户手机号
     */
    public void saveExUserPhone(Context context, String phone){
        SharePreferenceUtils.getDefault(context,className).put(exUserPhone,phone);
    }

    /**
     * 获取体验用户手机号
     */
    public String getExUserPhone(Context context){
        return SharePreferenceUtils.getDefault(context,className).get(exUserPhone,"");
    }

    /**
     * 检查用户数据是否为空
     * @return 是否空
     */
    public boolean checkHasUserInfo(Context context){
        return null == SharePreferenceUtils.getDefault(context,className).get(user,null);
    }

    /**
     * 获取用户数据实体
     * @return 用户实体
     */
    public UserEntity getData(Context context){
        if (null == entity) {
            String data =  SharePreferenceUtils.getDefault(context,className).get(user,"");
            if (!TextUtils.isEmpty(data)) {
                entity = GsonUtils.gsonToobj(data, UserEntity.class);
            }
        }
        return entity;
    }

    /**
     * 获取公司名称
     * @return companyName
     */
    public String getCompanyName(Context context) {
        return  SharePreferenceUtils.getDefault(context,className).get(companyName,"");
    }

    /**
     * 获取token
     * @return token
     */
    public String getToken(Context context){
        return  SharePreferenceUtils.getDefault(context,className).get(token,"");
    }

    /**
     * 获取用户id
     * @return 用户id
     */
    public long getUserId(Context context){
        try{
            return SharePreferenceUtils.getDefault(context,className).get(userId,(long)0);
        }catch (ClassCastException e){
            return 0;
        }
    }

    /**
     * 获取总店id
     * @return 总店id
     */
    public long getBusinessId(Context context){
        return SharePreferenceUtils.getDefault(context,className).get(business,(long)0);
    }

    /**
     * 获取用户类型
     * @return 0:普通用户  1：老板
     */
    public int getUserType(Context context){
        return  SharePreferenceUtils.getDefault(context,className).get(userType,0);
    }

    //获取首页引导配置
    public boolean getFirstGuideHome(Context context){
        return SharePreferenceUtils.getDefault(context,guide).get(firstGuideHome,true);
    }
    //设置首页引导配置
    public void setFirstGuideHome(Context context, boolean isGuide){
        SharePreferenceUtils.getDefault(context,guide).put(firstGuideHome,isGuide);
    }
    //获取设置页引导配置
    public boolean getFirstGuideSetting(Context context) {
        return SharePreferenceUtils.getDefault(context,guide).get(firstGuideSetting,true);
    }
    //获取首页引导配置
    public void setFirstGuideSetting(Context context, boolean isGuide){
        SharePreferenceUtils.getDefault(context,guide).put(firstGuideSetting,isGuide);
    }


}
