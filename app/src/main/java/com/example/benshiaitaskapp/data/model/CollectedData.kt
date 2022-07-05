package com.example.benshiaitaskapp.data.model

import com.google.android.gms.maps.model.LatLng

data class CollectedData(
    val appId :String,
    val action :String,
    val resourceId :String,
    val userId :String,
    val meta: UserMetaData
)
data class  UserMetaData(
    val time :String,
    val location :LatLng,
)