package com.jeremiahlewis.peoplemon.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jeremiahlewis on 11/7/16.
 */

public class TestPost {
    @SerializedName("userId")
    Integer userId;

    @SerializedName("id")
    Integer id;

    @SerializedName("title")
    String title;

    @SerializedName("body")
    String body;

    public TestPost() {
    }

    public TestPost(Integer userId, String title, String body) {
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId){
        this.userId = userId;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
