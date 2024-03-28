package com.example.internproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.internproject.databinding.ItemLayoutBinding

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
            binding.textUserName.text = stream.userName
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
