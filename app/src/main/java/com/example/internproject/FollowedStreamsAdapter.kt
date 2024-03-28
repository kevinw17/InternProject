package com.example.internproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.internproject.databinding.ItemLayoutBinding

class FollowedStreamsAdapter : ListAdapter<Stream, FollowedStreamsAdapter.FollowedStreamViewHolder>(StreamDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowedStreamViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowedStreamViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowedStreamViewHolder, position: Int) {
        val stream = getItem(position)
        holder.bind(stream)
    }

    class FollowedStreamViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(stream: Stream) {
            binding.textUserName.text = stream.userName
            binding.textViewerCount.text = "Viewers : ${stream.viewerCount}"

            val imageUrl = stream.thumbnailUrl.replace("{width}", TwitchConstants.IMAGE_WIDTH.toString())
                .replace("{height}", TwitchConstants.IMAGE_HEIGHT.toString())

            Glide.with(binding.imageView.context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.imageView)
        }
    }

    class StreamDiffCallback : DiffUtil.ItemCallback<Stream>() {
        override fun areItemsTheSame(oldItem: Stream, newItem: Stream): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Stream, newItem: Stream): Boolean {
            return oldItem == newItem
        }
    }
}
