package com.example.benshiaitaskapp.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.benshiaitaskapp.R
import com.example.benshiaitaskapp.adapter.CommentsAdapter
import com.example.benshiaitaskapp.adapter.PostsAdapter
import com.example.benshiaitaskapp.data.model.Post
import com.example.benshiaitaskapp.data.model.comments.CommentInfo
import com.example.benshiaitaskapp.databinding.FragmentPostsDetailBinding
import com.example.benshiaitaskapp.utils.HashUtils
import com.example.benshiaitaskapp.utils.toggleVisibility
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class PostsDetailFragment : Fragment(R.layout.fragment_posts_detail), OnMapReadyCallback {

    private var _binding: FragmentPostsDetailBinding? = null
    private val binding get() = _binding!!
    private var post: Post? = null
    private var userLocation: LatLng? = null
    lateinit var commentsAdapter  : CommentsAdapter
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            post = PostsDetailFragmentArgs.fromBundle(it).post
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentPostsDetailBinding.bind(view)

        if (post?.authorInfo != null){
            post?.authorInfo?.address?.geo?.let {
                userLocation = LatLng(it.lat?.toDouble()!!, it.lng?.toDouble()!!)
            }
        }else{
            //assign default location of Benshai office in spain
            userLocation = LatLng(41.393630, 2.163590)
        }

        commentsAdapter = CommentsAdapter{
            clicked(it)
        }

        post?.commentInfo?.let { loadComments(it) }

        val mapFragment = childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.apply {

            post?.apply {

                dTitle.text = "Title :  ${title}"
                dBody.text = "Body :  ${body}"
                dAuthorName.text = if (!authorInfo?.name.isNullOrEmpty()) "Author Name:  ${authorInfo?.name}" else "no author info yet"
                dAuthorEmail.text = if (!authorInfo?.email.isNullOrEmpty()) "Authorr Email:  ${authorInfo?.email}" else "no author info yet"
                dAuthorWebsite.text = if (!authorInfo?.website.isNullOrEmpty()) "Authorr Website:  ${authorInfo?.website}" else "no author info yet"
                dAuthorPhone.text = if (!authorInfo?.phone.isNullOrEmpty()) "Authorr Phone:  ${authorInfo?.phone}" else "no author info yet"

                dAuthorPhone.setOnClickListener {
                    post?.authorInfo?.phone?.let { it1 -> dialAuthorNumber(it1) }
                }
                dAuthorEmail.setOnClickListener {
                    post?.authorInfo?.email?.let { it1 -> sendEmail(it1) }
                }
                dAuthorWebsite.setOnClickListener {
                    post?.authorInfo?.website?.let { it1 -> openWebsite(it1) }
                }


                val imageLink = title
                val hashedLinkForSeed = imageLink?.let { HashUtils.sha256(it) }
                val getImageUrl = "https://picsum.photos/seed/$hashedLinkForSeed/200/200"


                   Glide.with(imageDetailLarge)
                       .load(getImageUrl)
                       .placeholder(R.drawable.ic_baseline_chat_24)
                       .error(R.drawable.ic_baseline_error_outline_24)
                       .fallback(R.drawable.ic_baseline_chat_24)
                       .into(imageDetailLarge)

            }

        }


    }

    private fun clicked(it: CommentInfo) {

    }

    private fun loadComments(comments: List<CommentInfo>) {

        if (!comments.isNullOrEmpty()){
            binding.apply {
                errorText.isVisible = false
                commentsAdapter.submitList(comments)
                rvComments.layoutManager = LinearLayoutManager(requireContext())
                rvComments.adapter = commentsAdapter
                val decoration = DividerItemDecoration(requireContext(), LinearLayout.VERTICAL)
                rvComments.addItemDecoration(decoration)
                progressBar.toggleVisibility(false)

            }
        }else{
            binding.apply {
                errorText.isVisible = false
                progressBar.toggleVisibility(false)
            }

        }



    }

    private fun dialAuthorNumber(contactNumber: String) {
        val callNcdc = Intent(Intent.ACTION_DIAL)
        callNcdc.data = Uri.parse("tel:$contactNumber")
        startActivity(callNcdc)
        return
    }

    fun sendEmail(address: String?) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.data = Uri.parse("mailto:$address")
        intent.type = "text/plain"
        startActivity(Intent.createChooser(intent, "Select your Email app"))
    }

    fun openWebsite(address: String?){
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.$address"))
        startActivity(browserIntent)
    }


    override fun onMapReady(gmap: GoogleMap) {
        if (gmap != null) {
            mMap = gmap
        }

        if (post?.authorInfo != null){
            mMap.addMarker(
                MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))

                    .position(LatLng( userLocation?.latitude!!, userLocation?.longitude!!)).title("Author  ${post?.authorInfo?.name} is here"))

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng( userLocation?.latitude!!, userLocation?.longitude!!), 20F), 4000, null)

        }


    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }




}