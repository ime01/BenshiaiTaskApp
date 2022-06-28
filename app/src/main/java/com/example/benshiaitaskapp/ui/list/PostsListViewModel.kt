package com.example.benshiaitaskapp.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.benshiaitaskapp.model.PostsList
import com.example.benshiaitaskapp.repository.PostsRepository
import com.example.benshiaitaskapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

import javax.inject.Inject


@HiltViewModel
class PostsListViewModel @Inject constructor(private val postsRepository: PostsRepository): ViewModel()  {




   fun getPosts():Flow<Resource<PostsList>> = flow{

       viewModelScope.launch(Dispatchers.IO){
          try {
            emit(Resource.Loading<PostsList>())

            val posts = postsRepository.fetchPosts()

            emit(Resource.Success(posts))

        }catch (e: HttpException){

            emit(Resource.Error<PostsList>(e.localizedMessage ?: "An unexpected error occurred"))

        }catch (e: IOException){

            emit(Resource.Error<PostsList>("Couldn't reach the server, Check your internet connection and try again"))
        }
    }

   }

}