package com.example.benshiaitaskapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import coil.load
import com.example.benshiaitaskapp.R
import com.example.benshiaitaskapp.data.model.Post
import com.example.benshiaitaskapp.data.model.comments.CommentInfo
import com.example.benshiaitaskapp.databinding.CommentListItemBinding
import com.example.benshiaitaskapp.databinding.PostListItemBinding
import com.example.benshiaitaskapp.utils.HashUtils.sha256
import java.util.Objects.hash


typealias commentdListener = (item: CommentInfo) -> Unit


class CommentsAdapter  (val listener: commentdListener)  :ListAdapter<CommentInfo, CommentsAdapter.ImageViewHolder>(
    CommentsDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_list_item, parent, false)

        return ImageViewHolder(CommentListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)) {
            getItem(it)?.let{item-> listener(item)}
        }

    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        val currentItem = getItem(position)

        holder.binding.apply {

            holder.itemView.apply {
                name.text = "Name: ${currentItem?.name?.take(20)}"
                email.text = "Email: ${currentItem?.email}"

                body.text = "Body: ${currentItem.body}"

            }
        }
    }

    inner class ImageViewHolder(val binding: CommentListItemBinding, private val listener: (Int)-> Unit): RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
                listener(adapterPosition)
            }
        }
    }


    interface RowClickListener{
        fun onItemClickListener(car: CommentInfo)

    }

}