package com.example.benshiaitaskapp.model
import com.google.gson.annotations.SerializedName


data class PostsList(val hits: List<Post>)

data class Post(
    @SerializedName("body")
    val body: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("userId")
    val userId: Int?
)