package com.foodloop.foodloopapps.ui.detailactivity

import androidx.lifecycle.ViewModel
import com.foodloop.foodloopapps.data.entity.BreadEntity
import com.foodloop.foodloopapps.utils.Data

class DetailViewModel : ViewModel() {
    private lateinit var breadId: String

    fun setSelectedBread(movieId: String) {
        this.breadId = movieId
    }

    fun getBread(): BreadEntity {
        lateinit var detailBread: BreadEntity
        val detailEntities = Data.generateDataBread()
        for (detailEntity in detailEntities) {
            if (detailEntity.breadId == breadId) {
                detailBread = detailEntity
            }
        }
        return detailBread
    }

    fun getDetailBread(): List<BreadEntity> = Data.generateDetailBread(breadId)
}