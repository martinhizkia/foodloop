package com.foodloop.foodloopapps.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foodloop.foodloopapps.BuildConfig.INFO_URL
import com.foodloop.foodloopapps.data.network.ApiConfig
import com.foodloop.foodloopapps.data.network.ApiService
import com.foodloop.foodloopapps.data.respons.InfoDetailRespons
import com.foodloop.foodloopapps.data.respons.ResultInfoRespons
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ViewModelHome : ViewModel() {
    val food = MutableLiveData<ArrayList<InfoDetailRespons>>()
    fun setFoodDetail() {
        val home = ApiConfig.getApiService(INFO_URL).create(
            ApiService::class.java
        )
        home.getInfo().enqueue(object : Callback<ResultInfoRespons> {
            override fun onResponse(
                call: Call<ResultInfoRespons>,
                response: Response<ResultInfoRespons>
            ) {
                if (response.isSuccessful) {
                    food.postValue(response.body()?.results)
                }
            }

            override fun onFailure(call: Call<ResultInfoRespons>, t: Throwable) {
                Log.e("LIST FOOD", "Failed: ${t.message.toString()}")
            }

        })
    }

    fun getFoodDetail(): LiveData<ArrayList<InfoDetailRespons>> {
        return food
    }
}

