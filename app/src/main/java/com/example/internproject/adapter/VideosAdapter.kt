package com.example.internproject.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.internproject.R
import com.example.internproject.databinding.ItemLayoutBinding
import com.example.internproject.model.Video
import com.example.internproject.view.activity.VideoDetailActivity

class VideosAdapter : ListAdapter<Video, VideosAdapter.VideosViewHolder>(VideosDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideosViewHolder, position: Int) {
        val video = getItem(position)
        holder.bind(video)
    }

    class VideosViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(video: Video) {
            binding.textView1.text = video.title
            binding.textViewerCount.text = "Total Views : ${video.viewCount}"

            val imageUrl = video.thumbnailUrl.replace("%{width}", "320")
                .replace("%{height}", "180")

            Glide.with(binding.imageView.context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.imageView)

            binding.root.setOnClickListener {
                val context = it.context
                val intent = Intent(context, VideoDetailActivity::class.java).apply {
                    putExtra("VIDEO_URL", video.url)
                    putExtra("VIDEO_TITLE", video.title)
                    putExtra("VIDEO_VIEW_COUNT", video.viewCount)
                    putExtra("VIDEO_TYPE", video.type)
                    putExtra("VIDEO_STREAMER", video.userName)
                }
                context.startActivity(intent)
            }

        }
    }

    class VideosDiffCallback : DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem == newItem
        }
    }
}