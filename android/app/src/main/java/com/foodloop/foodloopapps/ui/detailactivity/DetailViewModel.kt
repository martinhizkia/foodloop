package com.foodloop.foodloopapps.ui.detailactivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foodloop.foodloopapps.BuildConfig
import com.foodloop.foodloopapps.data.entity.BreadEntity
import com.foodloop.foodloopapps.data.network.ApiConfig
import com.foodloop.foodloopapps.data.network.ApiService
import com.foodloop.foodloopapps.data.respons.InfoDetailRespons
import com.foodloop.foodloopapps.data.respons.ResultInfoRespons
import com.foodloop.foodloopapps.utils.Data
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    val food = MutableLiveData<InfoDetailRespons>()
    fun setFoodDetail() {
        val home = ApiConfig.getApiService(BuildConfig.INFO_URL).create(
            ApiService::class.java
        )
        home.getInfo().enqueue(object : Callback<ResultRespons> {
            override fun onResponse(
                call: Call<ResultRespons>,
                response: Response<ResultRespons>
            ) {
                if (response.isSuccessful) {
                    food.postValue(response.body().result)
//                    Log.wtf("result0", response.body().toString())
                }
            }

            override fun onFailure(call: Call<ResultRespons>, t: Throwable) {
                Log.e("LOGIN", "Failed: ${t.message.toString()}")
            }

        })
    }
    fun getFoodDetail(): LiveData<InfoDetailRespons> {
        return food
    }
}