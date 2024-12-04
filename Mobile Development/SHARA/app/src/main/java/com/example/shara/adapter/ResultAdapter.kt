package com.example.shara.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shara.data.response.RecommendationsItem
import com.example.shara.databinding.ItemRowBinding

class RecommendationAdapter :
    ListAdapter<RecommendationsItem, RecommendationAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(private val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recommendation: RecommendationsItem) {
            binding.apply {
                tvItemName.text = recommendation.productName
                tvItemDescription.text = recommendation.description
                tvItemDescription2.text = recommendation.price
                Glide.with(itemView.context)
                    .load(recommendation.productImage)
                    .into(imgItemPhoto)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RecommendationsItem>() {
            override fun areItemsTheSame(
                oldItem: RecommendationsItem,
                newItem: RecommendationsItem
            ): Boolean {
                return oldItem.productId == newItem.productId
            }

            override fun areContentsTheSame(
                oldItem: RecommendationsItem,
                newItem: RecommendationsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
