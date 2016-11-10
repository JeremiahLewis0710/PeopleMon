package com.jeremiahlewis.peoplemon.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jeremiahlewis on 11/9/16.
 */

public class User {

    @SerializedName("UserId")
    private String UserId;

    @SerializedName("UserName")
    private String UserName;

    @SerializedName("AvatarBase64")
    private String AvatarBase64;

    @SerializedName("Longitude")
    private double Longitude;

    @SerializedName("Latitude")
    private double Latitude;

    @SerializedName("Created")
    private String Created;

    public User(){}

    public User(String userId, String userName, String avatarBase64, double longitude, double latitude) {
        UserId = userId;
        UserName = userName;
        AvatarBase64 = avatarBase64;
        Longitude = longitude;
        Latitude = latitude;
    }

    public User(double Latitude, double Longitude){
        this.Latitude = Latitude;
        this.Longitude = Longitude;
    }

    public User(String UserId, String UserName, String Created, String AvatarBase64){
        this.UserId = UserId;
        this.UserName = UserName;
        this.Created = Created;
        this.AvatarBase64 = AvatarBase64;
    }

    public User(String UserId){
        this.UserId = UserId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getAvatarBase64() {
        return AvatarBase64;
    }

    public void setAvatarBase64(String avatarBase64) {
        AvatarBase64 = avatarBase64;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public String getCreated() {
        return Created;
    }

    public void setCreated(String created) {
        Created = created;
    }
}
