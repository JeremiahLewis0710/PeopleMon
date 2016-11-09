package com.jeremiahlewis.peoplemon.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import static com.jeremiahlewis.peoplemon.Components.Constants.API_KEY;

/**
 * Created by jeremiahlewis on 11/7/16.
 */

public class Account {

    @SerializedName("Id")
    private String Id;

    @SerializedName("Email")
    private String Email;

    @SerializedName("FullName")
    private String FullName;

    @SerializedName("AvatarBase64")
    String AvatarBase64;

    @SerializedName("access_token")
    private String token;

    @SerializedName(".expires")
    private Date expiration;

    @SerializedName("password")
    private String password;

    @SerializedName("ApiKey")
    private String ApiKey;

    @SerializedName("Grant_Type")
    private String grant_type;

    @SerializedName("Latitude")
    private double Latitude;

    @SerializedName("Longitude")
    private double Longitude;

    public Account(){}

    public Account( String Email, String FullName, String AvatarBase64, String password){
        this.Email = Email;
        this.FullName = FullName;
        this.AvatarBase64 = AvatarBase64;
        this.ApiKey = API_KEY;
        this.password = password;

    }

    public Account(String grant_type, String FullName, String password){
        this.FullName = FullName;
        this.password = password;
        this.grant_type = grant_type;
    }

    public Account(double Longitude, double Latitude){
        this.Longitude = Longitude;
        this.Latitude = Latitude;
    }

    public Account(String AvatarBase64, String FullName){
        this.AvatarBase64 = AvatarBase64;
        this.FullName = FullName;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getAvatarBase64() {
        return AvatarBase64;
    }

    public void setAvatarBase64(String avatarBase64) {
        AvatarBase64 = avatarBase64;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }
}
