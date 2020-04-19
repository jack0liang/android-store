package com.gialen.baselib.bean.db_bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserInfo {
    int actionType;
    String birthday;
    String email;
    Long id;
    String loginId;
    String nickname;
    String phone;
    int platform;
    String recommendCode;
    int sex;
    Long storeId;
    String token;
    String userHeadPic;
    int userLevel;//1 vip 2 店主 3 店经 4 体验店主 5 体验店经
    String username;
    int hasPassword;//0 代表未设置 1代表已设置
    @Generated(hash = 2057774620)
    public UserInfo(int actionType, String birthday, String email, Long id,
            String loginId, String nickname, String phone, int platform,
            String recommendCode, int sex, Long storeId, String token,
            String userHeadPic, int userLevel, String username, int hasPassword) {
        this.actionType = actionType;
        this.birthday = birthday;
        this.email = email;
        this.id = id;
        this.loginId = loginId;
        this.nickname = nickname;
        this.phone = phone;
        this.platform = platform;
        this.recommendCode = recommendCode;
        this.sex = sex;
        this.storeId = storeId;
        this.token = token;
        this.userHeadPic = userHeadPic;
        this.userLevel = userLevel;
        this.username = username;
        this.hasPassword = hasPassword;
    }
    @Generated(hash = 1279772520)
    public UserInfo() {
    }
    public int getActionType() {
        return this.actionType;
    }
    public void setActionType(int actionType) {
        this.actionType = actionType;
    }
    public String getBirthday() {
        return this.birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLoginId() {
        return this.loginId;
    }
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
    public String getNickname() {
        return this.nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public int getPlatform() {
        return this.platform;
    }
    public void setPlatform(int platform) {
        this.platform = platform;
    }
    public String getRecommendCode() {
        return this.recommendCode;
    }
    public void setRecommendCode(String recommendCode) {
        this.recommendCode = recommendCode;
    }
    public int getSex() {
        return this.sex;
    }
    public void setSex(int sex) {
        this.sex = sex;
    }
    public Long getStoreId() {
        return this.storeId;
    }
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getUserHeadPic() {
        return this.userHeadPic;
    }
    public void setUserHeadPic(String userHeadPic) {
        this.userHeadPic = userHeadPic;
    }
    public int getUserLevel() {
        return this.userLevel;
    }
    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public int getHasPassword() {
        return this.hasPassword;
    }
    public void setHasPassword(int hasPassword) {
        this.hasPassword = hasPassword;
    }
}
