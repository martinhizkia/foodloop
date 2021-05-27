package com.foodloop.foodloopapps.data.network

import com.foodloop.foodloopapps.data.respons.UserRespons
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

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
}