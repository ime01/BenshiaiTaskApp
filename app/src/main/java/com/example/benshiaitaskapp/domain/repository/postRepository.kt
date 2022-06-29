package com.example.benshiaitaskapp.domain.repository

import com.example.benshiaitaskapp.data.model.Post
import com.example.benshiaitaskapp.utils.Resource

interface PostsRepository {

    suspend fun getPosts(): List<Post>
}