package com.foodloop.foodloopapps.ui.mypost.detailmypost

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foodloop.foodloopapps.BuildConfig
import com.foodloop.foodloopapps.data.network.ApiConfig
import com.foodloop.foodloopapps.data.network.ApiService
import com.foodloop.foodloopapps.data.respons.InfoDetailRespons
import com.foodloop.foodloopapps.data.respons.UserRespons
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMyPostViewModel : ViewModel() {
    val users = MutableLiveData<InfoDetailRespons>()
    fun setMypostDetail(id: Int) {
        val detail = ApiConfig.getApiService(BuildConfig.INFO_URL).create(
            ApiService::class.java
        )
        detail.getDetailFood(id).enqueue(object : Callback<InfoDetailRespons> {
            override fun onResponse(
                call: Call<InfoDetailRespons>,
                response: Response<InfoDetailRespons>
            ) {
                if (response.isSuccessful) {
                    users.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<InfoDetailRespons>, t: Throwable) {
                Log.d("onFailure", t.message.toString())
            }

        })
    }

    fun getMypostDetail(): LiveData<InfoDetailRespons> {
        return users
    }

    fun deletePost(id: Int, username: String) {
        val detail = ApiConfig.getApiService(BuildConfig.INFO_URL).create(
            ApiService::class.java
        )
        detail.deletePost(id, username).enqueue(object : Callback<UserRespons> {
            override fun onResponse(
                call: Call<UserRespons>,
                response: Response<UserRespons>
            ) {
                if (response.isSuccessful) {
                    response.body()
                }
            }

            override fun onFailure(call: Call<UserRespons>, t: Throwable) {
                Log.d("onFailure", t.message.toString())
            }

        })
    }
}