package com.exe.paradox.rest.response;

public class CreateUserRP {
    boolean is_new_user = false;
    boolean device_id_matched;
    String user_id;
    String name;

    public boolean isDevice_id_matched() {
        return device_id_matched;
    }

    public void setIs_new_user(boolean is_new_user) {
        this.is_new_user = is_new_user;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    String uid;

    public boolean isIs_new_user() {
        return is_new_user;
    }

    public CreateUserRP() {
    }

    String phone_number;

}
