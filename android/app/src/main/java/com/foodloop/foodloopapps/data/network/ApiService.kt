package com.foodloop.foodloopapps.data.network

import com.foodloop.foodloopapps.data.respons.InfoDetailRespons
import com.foodloop.foodloopapps.data.respons.ResultInfoRespons
import com.foodloop.foodloopapps.data.respons.StatusRespons
import com.foodloop.foodloopapps.data.respons.UserRespons
import retrofit2.Call
import retrofit2.http.*

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

//    @FormUrlEncoded
//    @DELETE("foodinfo")
//    fun deletePost(
//        @Field("id") id: Int,
//        @Field("username") username: String
//    ): Call<UserRespons>

    @DELETE("foodinfo/{username}/{id}")
    fun deletePost(
        @Path("username") username: String,
        @Path("id") id: Int
    ): Call<StatusRespons>

    @GET("foodinfo")
    fun getInfo(): Call<ResultInfoRespons>

    @GET("foodinfobyid/{id}")
    fun getDetailFood(
        @Path("id") id: Int?
    ): Call<InfoDetailRespons>

    @GET("foodinfo/{username}")
    fun getMyPost(
        @Path("username") username: String?
    ): Call<ResultInfoRespons>

    @GET("foodinfo/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<ResultInfoRespons>

}