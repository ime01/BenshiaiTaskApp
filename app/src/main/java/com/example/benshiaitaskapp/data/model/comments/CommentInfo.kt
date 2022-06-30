package com.example.benshiaitaskapp.data.model.comments


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CommentInfo(
    @SerializedName("body")
    val body: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("postId")
    val postId: Int?
): Parcelable