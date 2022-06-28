package com.example.benshiaitaskapp.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.benshiaitaskapp.model.Post


class PostsDiffCallback : DiffUtil.ItemCallback<Post>(){
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}