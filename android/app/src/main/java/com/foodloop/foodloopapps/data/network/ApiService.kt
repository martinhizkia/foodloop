package com.foodloop.foodloopapps.data.network

import android.widget.ImageButton
import com.foodloop.foodloopapps.data.respons.*
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.Path
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.GET

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    fun postLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<UserRespons>

    @FormUrlEncoded
    @POST("signup")
    fun createUser(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("fullname") fullname: String,
        @Field("email") email: String
    ): Call<UserRespons>

    @FormUrlEncoded
    @POST("foodinfo")
    fun postInfo(
        @Field("username") username: String?,
        @Field("foodname") foodname: String,
        @Field("address") address: String,
        @Field("description") description: String,
        @Field("price") price: String,
        @Field("contact") contact: String,
        @Field("category") category: String,
        @Field("img") img: String,
    ): Call<UserRespons>

    @GET("foodinfo/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<ResultRespons>

}