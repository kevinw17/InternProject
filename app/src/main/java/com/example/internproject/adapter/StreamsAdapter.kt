package com.example.internproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.internproject.R
import com.example.internproject.databinding.ItemLayoutBinding
import com.example.internproject.model.Stream
import com.example.internproject.util.TwitchConstants

class StreamsAdapter : ListAdapter<Stream, StreamsAdapter.StreamViewHolder>(StreamDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreamViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StreamViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StreamViewHolder, position: Int) {
        val stream = getItem(position)
        holder.bind(stream)
    }

    class StreamViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(stream: Stream) {
            binding.textView1.text = stream.userName
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
