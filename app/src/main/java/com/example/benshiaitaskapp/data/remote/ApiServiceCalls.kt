package com.example.benshiaitaskapp.data.remote

import com.example.benshiaitaskapp.data.model.Post
import com.example.benshiaitaskapp.data.model.authorinfo.AuthorInfo
import com.example.benshiaitaskapp.data.model.comments.CommentInfo
import com.example.benshiaitaskapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServiceCalls {

    @GET(Constants.POST_URL)
    suspend fun getPosts() : List<Post>

    @GET("users/{id}")
    suspend fun getAuthorInfo(@Path("id")Id: String) : AuthorInfo


    @GET("comments")
    suspend fun getComments(@Query("postId")postId: String) : List<CommentInfo>
}