package com.example.shara.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shara.data.response.ArticlesItem
import com.example.shara.databinding.ItemNewsBinding
import com.example.shara.util.DateFormatter

class NewsAdapter: ListAdapter<ArticlesItem, NewsAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = getItem(position)
        news?.let { holder.bind(it) }
    }

    class ViewHolder(private val binding: ItemNewsBinding):
        RecyclerView.ViewHolder(binding.root){
            fun bind(news: ArticlesItem){
                binding.tvItemTitle.text = news.title
                binding.tvItemPublishedDate.text = news.publishedAt.let {
                    DateFormatter.formatDate(it)
                } ?: "No Date"
                news.urlToImage.let { imageUrl ->
                    Glide.with(itemView.context)
                        .load(imageUrl)
                        .into(binding.imgPoster)
                }
            }

    }



    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticlesItem>() {
            override fun areItemsTheSame(
                oldItem: ArticlesItem,
                newItem: ArticlesItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ArticlesItem,
                newItem: ArticlesItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}