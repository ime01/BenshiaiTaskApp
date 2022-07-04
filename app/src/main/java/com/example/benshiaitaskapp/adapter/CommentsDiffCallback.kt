package com.example.benshiaitaskapp.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.benshiaitaskapp.data.model.Post
import com.example.benshiaitaskapp.data.model.comments.CommentInfo


class CommentsDiffCallback : DiffUtil.ItemCallback<CommentInfo>(){
    override fun areItemsTheSame(oldItem: CommentInfo, newItem: CommentInfo): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: CommentInfo, newItem: CommentInfo): Boolean {
        return oldItem == newItem
    }
}