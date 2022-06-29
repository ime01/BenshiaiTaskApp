package com.example.benshiaitaskapp.data.repository

import com.example.benshiaitaskapp.data.model.Post
import com.example.benshiaitaskapp.domain.repository.PostsRepository
import com.example.benshiaitaskapp.data.remote.ApiServiceCalls
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val api : ApiServiceCalls) :PostsRepository {

    override suspend fun getPosts(): List<Post>{
       return api.getPosts()
    }


}