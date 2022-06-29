package com.example.benshiaitaskapp.data.remote

import com.example.benshiaitaskapp.data.model.Post
import com.example.benshiaitaskapp.data.model.authorinfo.AuthorInfo
import com.example.benshiaitaskapp.utils.Constants
import retrofit2.http.GET

interface ApiServiceCalls {

    @GET(Constants.POST_URL)
    suspend fun getPosts() : List<Post>

    @GET()
    suspend fun getAuthorInfo() : AuthorInfo
}