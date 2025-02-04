package com.iustu.identification.entity;

import android.content.ContentValues;

/**
 * created by sgh, 2019-4-2
 *
 * 用来记录对比记录的数据表
 */
public class CompareRecord {
    private String hourTime;               // 时分秒
    private String originalPhoto;          // 原始图片
    private String uploadPhoto;          // 对比时上传的裁剪之后的图片
    private String time;             // 时间戳， 比对的时间
    private String image_id;           // 对比结果对应的人脸库中的人脸
    private float rate;              // 比对的相似度
    private boolean isExtend = false;    //是否展开,不作为数据表中的属性
    private String libName;            // 该属性并不绑定于一个Library，只是用来在创建
    private String name;
    private String gender;        // 性别
    private String home;           // 籍贯(选)
    private String other;         // 备注
    private String identity;
    private String photoPath;
    private String birthday;


    public String getUploadPhoto() {
        return uploadPhoto;
    }

    public void setUploadPhoto(String uploadPhotoPath) {
        this.uploadPhoto = uploadPhotoPath;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public boolean isExtend() {
        return isExtend;
    }

    public void setExtend(boolean extend) {
        isExtend = extend;
    }
    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getImage_id() {
        return this.image_id;
    }

    public void setLibName(String libName) {
        this.libName = libName;
    }

    public String getLibName() {
        return libName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return this.gender;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getHome() {
        return home;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getOther() {
        return other;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getIdentity() {
        return this.identity;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getPhotoPath() {
        return this.photoPath;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getBirthday() {
        return birthday;
    }

    public void setOriginalPhoto(String originalPhoto) {
        this.originalPhoto = originalPhoto;
    }

    public String getOriginalPhoto() {
        return this.originalPhoto;
    }

    public String getHourTime() {
        return this.hourTime;
    }

    public void setHourTime(String hourTime) {
        this.hourTime = hourTime;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put("time", this.time);
        values.put("uploadPhoto", uploadPhoto);
        values.put("rate", this.rate);
        values.put("image_id", image_id);
        values.put("libName", libName);
        values.put("other", other);
        values.put("name", name);
        values.put("gender", gender);
        values.put("home", home);
        values.put("photoPath", photoPath);
        values.put("birthday", birthday);
        values.put("originalPhoto", this.originalPhoto);
        values.put("hourTime", this.hourTime);
        values.put("identity",this.identity);
        return values;
    }
}
