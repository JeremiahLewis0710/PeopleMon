package com.jeremiahlewis.peoplemon.Network;

import com.jeremiahlewis.peoplemon.Models.Account;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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







}
