package com.example.benshiaitaskapp.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.benshiaitaskapp.data.model.Post
import com.example.benshiaitaskapp.data.model.authorinfo.AuthorInfo
import com.example.benshiaitaskapp.data.model.comments.CommentInfo
import com.example.benshiaitaskapp.domain.usecases.GetAuthorInfoUseCase
import com.example.benshiaitaskapp.domain.usecases.GetCommentsUseCase
import com.example.benshiaitaskapp.domain.usecases.GetPostsUseCase
import com.example.benshiaitaskapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

import javax.inject.Inject


enum class  PostsApiStatus {LOADING, ERROR, DONE}

@HiltViewModel
class PostsListViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val getAuthorInfoUseCase: GetAuthorInfoUseCase,
    private val getCommentsUseCase: GetCommentsUseCase): ViewModel()  {

    val postsFromNetwork = MutableLiveData<List<Post>>()
    val authorInfoFromAPost = MutableLiveData<AuthorInfo>()
    val commentsFromAPost = MutableLiveData<List<CommentInfo>>()
    val errorMessage = MutableStateFlow<String?>(null)
    val postNetworkStatus = MutableLiveData<PostsApiStatus>()


    fun getPosts(){

        getPostsUseCase.invoke().onEach { result->

            when(result){
                is  Resource.Success ->{

                    postNetworkStatus.value = PostsApiStatus.DONE
                    postsFromNetwork.postValue(result.data!!)

                }
                is Resource.Error ->{
                    postNetworkStatus.value = PostsApiStatus.ERROR
                    errorMessage.value = result.message
                }

                is Resource.Loading->{
                    postNetworkStatus.value = PostsApiStatus.LOADING
                }

            }
        }.launchIn(viewModelScope)
    }




    fun getAuthorInfo(userId:String){

        getAuthorInfoUseCase.invoke(userId).onEach { result->

            when(result){
                is  Resource.Success ->{

                    postNetworkStatus.value = PostsApiStatus.DONE
                    authorInfoFromAPost.postValue(result.data!!)

                }
                is Resource.Error ->{
                    postNetworkStatus.value = PostsApiStatus.ERROR
                    errorMessage.value = result.message
                }

                is Resource.Loading->{
                    postNetworkStatus.value = PostsApiStatus.LOADING
                }

            }
        }.launchIn(viewModelScope)
    }


    fun getComments(postId:String){

        getCommentsUseCase.invoke(postId).onEach { result->

            when(result){
                is  Resource.Success ->{

                    postNetworkStatus.value = PostsApiStatus.DONE
                    commentsFromAPost.postValue(result.data!!)

                }
                is Resource.Error ->{
                    postNetworkStatus.value = PostsApiStatus.ERROR
                    errorMessage.value = result.message
                }

                is Resource.Loading->{
                    postNetworkStatus.value = PostsApiStatus.LOADING
                }

            }
        }.launchIn(viewModelScope)
    }
}