package com.jeremiahlewis.peoplemon.Network;

import com.jeremiahlewis.peoplemon.Models.Account;
import com.jeremiahlewis.peoplemon.Models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by jeremiahlewis on 11/7/16.
 */

public interface ApiService {
   @POST("/api/Account/Register")
    Call<Void>register(@Body Account account);

    @FormUrlEncoded
    @POST("/token")
    Call<Account> login(@Field(value = "grant_type", encoded = true) String grantType,
               @Field(value = "username", encoded = true) String username,
               @Field(value = "password", encoded = true ) String password);

    @POST("v1/User/CheckIn")
    Call<Void> checkin(@Body Account account);

    @GET("v1/User/Nearby")
    Call<User>findUsersNearby(@Path("UserId")String UserId,
                              @Path("UserName") String UserName,
                              @Path("AvatarBase64") String AvatarBase64,
                              @Path("Longitude") double Longitude,
                              @Path("Latitude") double Latitude,
                              @Path("Created") String Created);

    //Get User Info Call
    @GET("/api/Account/UserInfo")
    Call<Account>getUserInfo();

    @POST("/api/Account/UserInfo")
    Call<Void>postUserInfo(@Body Account account);








}
