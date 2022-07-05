package com.example.benshiaitaskapp.ui.list

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.benshiaitaskapp.R
import com.example.benshiaitaskapp.adapter.PostsAdapter
import com.example.benshiaitaskapp.databinding.FragmentPostsBinding
import com.example.benshiaitaskapp.data.model.Post
import com.example.benshiaitaskapp.data.model.authorinfo.AuthorInfo
import com.example.benshiaitaskapp.data.model.comments.CommentInfo
import com.example.benshiaitaskapp.presentation.AuthorApiStatus
import com.example.benshiaitaskapp.presentation.CommentsApiStatus
import com.example.benshiaitaskapp.presentation.PostsApiStatus
import com.example.benshiaitaskapp.presentation.PostsListViewModel
import com.example.benshiaitaskapp.ui.details.PostsDetailFragmentArgs
import com.example.benshiaitaskapp.utils.getConnectionType
import com.example.benshiaitaskapp.utils.showSnackbar
import com.example.benshiaitaskapp.utils.toggleVisibility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class PostsFragment : Fragment(R.layout.fragment_posts) {

    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!
    private var errorMessage: String? = null
    lateinit var postsAdapter  : PostsAdapter
    private var postsWithCommentsandAuthorInfo = MutableLiveData<List<Post>>()


    private val viewModel: PostsListViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentPostsBinding.bind(view)

        showWelcomeMarqueeText()

        if (postsWithCommentsandAuthorInfo.value.isNullOrEmpty()) viewModel.getPosts()


        postsAdapter = PostsAdapter{
            transitionToDetailView(it)
        }

        setObservers()
    }

    private fun getErrorMessage() {
        lifecycleScope.launchWhenResumed {
            viewModel.errorMessage.collect {
                if (it != null) {
                    errorMessage = it
                }
            }
        }
    }

    fun observeFetchPostsState(){

        binding.apply {

            viewModel.postNetworkStatus.observe(viewLifecycleOwner, Observer { state ->

                state?.also {
                    when (it) {
                        PostsApiStatus.ERROR -> {

                            errorMessage?.let { it1 -> showSnackbar(welcomeTextMarquee, it1) }

                            errorImage.toggleVisibility(true)
                            errorText.toggleVisibility(true)

                        }
                        PostsApiStatus.LOADING -> {

                            shimmerFrameLayout.startShimmer()
                            shimmerFrameLayout.toggleVisibility(true)

                        }

                        PostsApiStatus.DONE -> {

                            viewModel.postsFromNetwork.observe(viewLifecycleOwner, Observer {
                                postsWithCommentsandAuthorInfo.postValue(it)

                                loadRecyclerView(it)
                            })


                            postsWithCommentsandAuthorInfo.observe(viewLifecycleOwner, Observer {

                                it.forEach {
                                    fetchCommentsAndAuthorInfo(it.userId.toString(), it.id.toString() )
                                    Toast.makeText(requireContext(), "COMMENTS FETCHED", Toast.LENGTH_SHORT).show()
                                }

                            })

                        }

                    }
                }

            })
        }

    }


    fun observeFetchAuthorInfoState(){

        binding.apply {

            viewModel.authorNetworkStatus.observe(viewLifecycleOwner, Observer { state ->

                state?.also {
                    when (it) {
                        AuthorApiStatus.ERROR -> {
                            //errorGettingAuthorInfo(it)

                        }
                        AuthorApiStatus.LOADING -> {
                        }

                        AuthorApiStatus.DONE -> {

                            viewModel.authorInfoFromAPost.observe(viewLifecycleOwner, Observer {author->

                                println("Author Gotten ${author}")
                                Log.d("Author Gotten"," ${author}")

                                postsWithCommentsandAuthorInfo.observe(viewLifecycleOwner, Observer {

                                    it.forEach {
                                        if (it.userId == author.id) it.authorInfo = author
                                    }
                                })

                            })

                        }

                    }
                }

            })
        }

    }

    private fun errorGettingAuthorInfo(error:String) {
        Toast.makeText(requireContext(), "Error Getting Author info  $error", Toast.LENGTH_SHORT).show()
    }


    fun observeFetchCommentsState(){

        binding.apply {

            viewModel.commentsNetworkStatus.observe(viewLifecycleOwner, Observer { state ->

                state?.also {
                    when (it) {
                        CommentsApiStatus.ERROR -> {

                        }
                        CommentsApiStatus.LOADING -> {

                        }

                        CommentsApiStatus.DONE -> {

                            viewModel.commentsFromAPost.observe(viewLifecycleOwner, Observer {allcommentInfo->

                                println("COMMENTS GOTTEN ${allcommentInfo}")
                                Log.d("COMMENTS GOTTEN"," ${allcommentInfo}")

                                allcommentInfo.forEach {oneCommentInfo->

                                    postsWithCommentsandAuthorInfo.observe(viewLifecycleOwner, Observer {

                                        it.forEach {onePost->
                                            if (onePost.id == oneCommentInfo.postId) onePost.commentInfo = allcommentInfo
                                        }
                                    })
                                }

                            })



                        }

                    }
                }

            })
        }

    }

    fun fetchCommentsAndAuthorInfo(userId:String, postId:String){

     if (!userId.isNullOrEmpty())viewModel.getAuthorInfo(userId)
     if (!postId.isNullOrEmpty()) viewModel.getComments(postId)


    }

    fun setObservers(){
        observeFetchPostsState()
        observeFetchAuthorInfoState()
        observeFetchCommentsState()
        getErrorMessage()

        postsWithCommentsandAuthorInfo.observe(viewLifecycleOwner, Observer {
            loadRecyclerView(it)
        })

    }


    private fun showWelcomeMarqueeText() {

        binding.apply {

            welcomeTextMarquee.apply {
                setSingleLine()
                ellipsize = TextUtils.TruncateAt.MARQUEE
                marqueeRepeatLimit = -1
                isSelected = true
            }
        }
    }

    private fun transitionToDetailView(post: Post) {
        val action = PostsFragmentDirections.actionPostsFragmentToPostsDetailFragment()
        action.post = post
        Navigation.findNavController(requireView()).navigate(action)
    }


    private fun loadRecyclerView(posts: List<Post>) {

        binding.apply {
            errorImage.isVisible = false
            postsAdapter.submitList(posts)
            rvList.layoutManager = LinearLayoutManager(requireContext())
            rvList.adapter = postsAdapter
            val decoration = DividerItemDecoration(requireContext(), LinearLayout.VERTICAL)
            rvList.addItemDecoration(decoration)

            shimmerFrameLayout.stopShimmer()
            shimmerFrameLayout.toggleVisibility(false)

        }

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu1_layout, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){

            R.id.settings_view ->{
                findNavController().navigate(R.id.action_postsFragment_to_settingsFragment)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}