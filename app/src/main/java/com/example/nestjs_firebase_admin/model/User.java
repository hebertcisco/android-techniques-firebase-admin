package com.example.nestjs_firebase_admin.model;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String name;
    private String fcm_token;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public User(String name, String fcm_token) {
        this.name = name;
        this.fcm_token = fcm_token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFcm_token() {
        return fcm_token;
    }

    public void setFcm_token(String fcm_token) {
        this.fcm_token = fcm_token;
    }
}