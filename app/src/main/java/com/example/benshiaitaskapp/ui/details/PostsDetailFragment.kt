package com.example.benshiaitaskapp.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.benshiaitaskapp.R
import com.example.benshiaitaskapp.data.model.Post
import com.example.benshiaitaskapp.databinding.FragmentPostsDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsDetailFragment : Fragment(R.layout.fragment_posts_detail) {

    private var _binding: FragmentPostsDetailBinding? = null
    private val binding get() = _binding!!
    private var post: Post? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            post = PostsDetailFragmentArgs.fromBundle(it).post
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentPostsDetailBinding.bind(view)

        binding.apply {

            post?.apply {

                duserName.text = "Title :  ${title}"
                dinnerCleanliness.text = "UserId :  ${userId}"
                dseries.text = "Id :  ${id}"

              /*  dmodelName.text = "Model Name :  ${modelName}"
                dmake.text = "Make :  ${make}"
                dlongitude.text = "Longitude :  ${longitude}"
                dlatitude.text = "Latitude :  ${latitude}"
                dgroup.text = "Group :  ${group}"
                dfuelLevel.text = "Fuel Level :  ${fuelLevel}"*/
            }


         /*   Glide.with(imageDetailLarge)
                .load(car?.carImageUrl)
                .placeholder(R.drawable.bmw)
                .error(R.drawable.bmw)
                .fallback(R.drawable.bmw)
                .into(imageDetailLarge)*/


        }


    }







    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}