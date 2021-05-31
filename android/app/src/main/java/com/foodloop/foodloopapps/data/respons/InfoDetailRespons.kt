package com.foodloop.foodloopapps.data.respons

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InfoDetailRespons(
	val id: Int? = null,
	val img: String? = null,
	val foodname: String? = null,
	val address: String? = null,
	val price: Int? = 0,
	val contact: String? = null,
	val description: String? = null,
	val category: String? = null,
	val username: String? = null
):Parcelable
