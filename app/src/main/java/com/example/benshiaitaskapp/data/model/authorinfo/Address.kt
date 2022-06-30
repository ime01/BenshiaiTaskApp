package com.example.benshiaitaskapp.data.model.authorinfo


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    @SerializedName("city")
    val city: String?,
    @SerializedName("geo")
    val geo: Geo?,
    @SerializedName("street")
    val street: String?,
    @SerializedName("suite")
    val suite: String?,
    @SerializedName("zipcode")
    val zipcode: String?
): Parcelable