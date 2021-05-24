package com.foodloop.foodloopapps.ui.home

import androidx.lifecycle.ViewModel
import com.foodloop.foodloopapps.data.entity.BreadEntity
import com.foodloop.foodloopapps.utils.Data

class ViewModelBread: ViewModel() {
    fun getMovies(): List<BreadEntity> = Data.generateDataBread()
}