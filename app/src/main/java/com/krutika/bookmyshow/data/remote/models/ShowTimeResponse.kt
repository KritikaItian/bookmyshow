package com.krutika.bookmyshow.data.remote.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ShowTimeResponse(

    @field:SerializedName("venues")
    val venues: ArrayList<VenuesItem>? = null
):Serializable

data class ShowtimesItem(

    @field:SerializedName("showDateCode")
    val showDateCode: Long? = null,

    @field:SerializedName("showTime")
    val showTime: String? = null
):Serializable

data class VenuesItem(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("showtimes")
    val showtimes: ArrayList<ShowtimesItem?>? = null,

    @field:SerializedName("showDate")
    val showDate: String? = null
):Serializable
