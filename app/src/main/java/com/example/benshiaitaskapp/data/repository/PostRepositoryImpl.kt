package com.example.benshiaitaskapp.data.repository

import com.example.benshiaitaskapp.data.model.Post
import com.example.benshiaitaskapp.data.model.authorinfo.AuthorInfo
import com.example.benshiaitaskapp.data.model.comments.CommentInfo
import com.example.benshiaitaskapp.domain.repository.PostsRepository
import com.example.benshiaitaskapp.data.remote.ApiServiceCalls
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val api : ApiServiceCalls) :PostsRepository {

    override suspend fun getPosts(): List<Post>{
       return api.getPosts()
    }

    override suspend fun getAuthorInfo(userId:String): AuthorInfo {
        return api.getAuthorInfo(userId)
    }

    override suspend fun getComments(postId: String): CommentInfo {
        return api.getComments(postId)
    }


}