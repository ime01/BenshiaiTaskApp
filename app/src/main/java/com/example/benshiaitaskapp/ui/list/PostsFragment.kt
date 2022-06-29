package com.example.benshiaitaskapp.ui.list

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.benshiaitaskapp.R
import com.example.benshiaitaskapp.adapter.PostsAdapter
import com.example.benshiaitaskapp.databinding.FragmentPostsBinding
import com.example.benshiaitaskapp.data.model.Post
import com.example.benshiaitaskapp.presentation.PostsApiStatus
import com.example.benshiaitaskapp.presentation.PostsListViewModel
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


    private val viewModel: PostsListViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentPostsBinding.bind(view)

        showWelcomeMarqueeText()

        viewModel.getPosts()

        postsAdapter = PostsAdapter{
            transitionToDetailView(it)
        }

        observeState()
        getErrorMessage()


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

    fun observeState(){

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
                                loadRecyclerView(it)
                            })

                        }

                    }
                }

            })
        }

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

            openMapView.setOnClickListener {
               // Navigation.findNavController(requireView()).navigate(R.id.action_carsListFragment_to_mapFragment)
            }
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}