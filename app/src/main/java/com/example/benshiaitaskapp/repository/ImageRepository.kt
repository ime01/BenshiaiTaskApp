package com.example.benshiaitaskapp.repository


import com.example.benshiaitaskapp.model.PostsList
import com.example.benshiaitaskapp.network.ApiServiceCalls
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PostsRepository @Inject constructor(private val apiClient: ApiServiceCalls) {


    suspend fun fetchPosts(): PostsList{
        return apiClient.getPosts()
    }


}