package com.scan.buxiaosheng.Entity;

import java.io.Serializable;

/**
 * 用户实体
 * Created by BertramTan on 2018/5/10
 */
public class UserEntity implements Serializable {

    /**
     * loginName : 测试内容s13g
     * pwdResetStaus : 81855
     * token : 测试内容c4uu
     * type : 24548
     * userId : 30334
     */

    private String companyName;
    private int pwdResetStaus;
    private String token;
    private int type; //0 员工 1老板
    private long userId;
    private int companyId;
    private int companyType;//0普通 1付费
    private int amountType;//0体验用户，1正常用户
    private String userName;
    private long businessId;//总店

    public long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(long businessId) {
        this.businessId = businessId;
    }

    public int getAmountType() {
        return amountType;
    }

    public void setAmountType(int amountType) {
        this.amountType = amountType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCompanyType() {
        return companyType;
    }

    public void setCompanyType(int companyType) {
        this.companyType = companyType;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String loginName) {
        this.companyName = loginName;
    }

    public int getPwdResetStaus() {
        return pwdResetStaus;
    }

    public void setPwdResetStaus(int pwdResetStaus) {
        this.pwdResetStaus = pwdResetStaus;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
