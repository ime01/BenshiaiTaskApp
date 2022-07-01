package com.example.benshiaitaskapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.example.benshiaitaskapp.R
import com.example.benshiaitaskapp.data.model.Post
import com.example.benshiaitaskapp.databinding.PostListItemBinding


typealias urlListener = (item: Post) -> Unit


class PostsAdapter  (val listener: urlListener)  :ListAdapter<Post, PostsAdapter.ImageViewHolder>(
    PostsDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_list_item, parent, false)

        return ImageViewHolder(PostListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)) {
            getItem(it)?.let{item-> listener(item)}
        }

    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        val currentItem = getItem(position)

        holder.binding.apply {

            holder.itemView.apply {
                title.text = "Title: ${currentItem.title?.take(20)}"
                body.text = "Body: ${currentItem.body?.take(100)}"
                if (currentItem.authorInfo?.name.isNullOrEmpty()){
                    authorName.text = "John doe"
                }else{
                    authorName.text = currentItem?.authorInfo?.name
                }

                if (currentItem.commentInfo.isNullOrEmpty()){
                    totalComments.text = "0"
                }else{
                    totalComments.text = currentItem?.commentInfo?.size.toString()
                }


              /*  val imageLink = currentItem?.carImageUrl

                imageThumbail.load(imageLink){
                    error(R.drawable.bmw)
                    placeholder(R.drawable.bmw)
                    crossfade(true)
                    crossfade(1000)
                }*/
            }
        }
    }

    inner class ImageViewHolder(val binding: PostListItemBinding, private val listener: (Int)-> Unit): RecyclerView.ViewHolder(binding.root){

        init {
            binding.root.setOnClickListener {
                listener(adapterPosition)
            }
        }
    }


    interface RowClickListener{
        fun onItemClickListener(car: Post)

    }

}