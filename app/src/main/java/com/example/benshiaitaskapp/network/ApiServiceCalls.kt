package com.example.benshiaitaskapp.network

import com.example.benshiaitaskapp.model.Post
import com.example.benshiaitaskapp.model.PostsList
import com.example.benshiaitaskapp.utils.Constants
import retrofit2.http.GET

interface ApiServiceCalls {

    @GET(Constants.POST_URL)
    suspend fun getPosts() : PostsList
}