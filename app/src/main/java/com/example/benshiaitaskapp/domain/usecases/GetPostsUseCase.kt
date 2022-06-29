package com.example.benshiaitaskapp.domain.usecases

import com.example.benshiaitaskapp.data.model.Post
import com.example.benshiaitaskapp.domain.repository.PostsRepository
import com.example.benshiaitaskapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPostsUseCase @Inject constructor (private val repository: PostsRepository) {


    operator fun invoke (): Flow<Resource<List<Post>>> = flow {

        try {
            emit(Resource.Loading<List<Post>>())

            val posts = repository.getPosts()

            emit(Resource.Success(posts))

        }catch (e:HttpException){

            emit(Resource.Error<List<Post>>(e.localizedMessage ?: "An unexpected error occurred"))

        }catch (e: IOException){

            emit(Resource.Error<List<Post>>("Couldn't reach the server, Check your internet connection and try again"))
        }
    }
}