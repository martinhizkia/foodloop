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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelMypost: ViewModel() {
    val listFood = MutableLiveData<ArrayList<InfoDetailRespons>>()
    fun setMypostFoodDetail(username: String) {
        val home = ApiConfig.getApiService(BuildConfig.INFO_URL).create(
            ApiService::class.java
        )
        home.getUserDetail(username).enqueue(object : Callback<ResultInfoRespons> {
            override fun onResponse(
                call: Call<ResultInfoRespons>,
                response: Response<ResultInfoRespons>
            ) {
                if (response.isSuccessful) {
                    listFood.postValue(response.body()?.result)
                }
            }
            override fun onFailure(call: Call<ResultInfoRespons>, t: Throwable) {
                Log.e("LIST MYPOST", "Failed: ${t.message.toString()}")
            }

        })
    }
    fun getMypostFoodDetail(): LiveData<ArrayList<InfoDetailRespons>> {
        return listFood
    }
}