package com.example.benshiaitaskapp.domain.repository

import com.example.benshiaitaskapp.data.model.Post
import com.example.benshiaitaskapp.data.model.authorinfo.AuthorInfo
import com.example.benshiaitaskapp.data.model.comments.CommentInfo
import com.example.benshiaitaskapp.utils.Resource

interface PostsRepository {

    suspend fun getPosts(): List<Post>

    suspend fun getAuthorInfo(userId:String): AuthorInfo

    suspend fun getComments(postId: String): CommentInfo
}