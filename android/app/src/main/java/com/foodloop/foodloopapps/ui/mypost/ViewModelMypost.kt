package com.foodloop.foodloopapps.ui.mypost

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foodloop.foodloopapps.BuildConfig
import com.foodloop.foodloopapps.data.network.ApiConfig
import com.foodloop.foodloopapps.data.network.ApiService
import com.foodloop.foodloopapps.data.respons.InfoDetailRespons
import com.foodloop.foodloopapps.data.respons.ResultInfoRespons
import com.foodloop.foodloopapps.data.respons.UserRespons
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelMypost: ViewModel() {
    val food = MutableLiveData<ArrayList<InfoDetailRespons>>()
    fun setMypost(username: String) {
        val detail = ApiConfig.getApiService(BuildConfig.INFO_URL).create(
            ApiService::class.java
        )
        detail.getMyPost(username).enqueue(object : Callback<ResultInfoRespons> {
            override fun onResponse(
                call: Call<ResultInfoRespons>,
                response: Response<ResultInfoRespons>
            ) {
                if (response.isSuccessful) {
                    food.value = response.body()?.result
                }
            }

            override fun onFailure(call: Call<ResultInfoRespons>, t: Throwable) {
                Log.d("onFailure", t.message.toString())
            }

        })
    }

    fun getMypost(): LiveData<ArrayList<InfoDetailRespons>> {
        return food
    }
}