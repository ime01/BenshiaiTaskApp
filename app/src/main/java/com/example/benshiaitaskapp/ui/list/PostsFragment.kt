package com.example.benshiaitaskapp.ui.list

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.benshiaitaskapp.R
import com.example.benshiaitaskapp.adapter.PostsAdapter
import com.example.benshiaitaskapp.databinding.FragmentPostsBinding
import com.example.benshiaitaskapp.model.Post
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostsFragment : Fragment(R.layout.fragment_posts) {

    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!
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

   /* private fun transitionToDetailView(car: Car) {

        val action = CarsListFragmentDirections.actionCarsListFragmentToCarsDetailsFragment()
        action.car = car
        Navigation.findNavController(requireView()).navigate(action)
    }*/


    private fun loadRecyclerView(posts: List<Post>) {

        binding.apply {
            errorImage.isVisible = false
            postsAdapter.submitList(posts)
            rvList.layoutManager = LinearLayoutManager(requireContext())
            rvList.adapter = postsAdapter
            val decoration = DividerItemDecoration(requireContext(), LinearLayout.VERTICAL)
            rvList.addItemDecoration(decoration)

            shimmerFrameLayout.stopShimmer()
            shimmerFrameLayout.visibility = View.GONE

            openMapView.setOnClickListener {
                Navigation.findNavController(requireView()).navigate(R.id.action_carsListFragment_to_mapFragment)
            }
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}