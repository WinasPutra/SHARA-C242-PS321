package com.example.shara.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.DiffUtil
import com.example.shara.data.response.HistoryItem
import com.example.shara.databinding.HistoryRowBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class HistoryAdapter :
    ListAdapter<HistoryItem, HistoryAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(private val binding: HistoryRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(history: HistoryItem) {
            binding.apply {
                // Tampilkan skin type
                histTvItemName.text = "Skin Type: ${history.skinType ?: "Unknown"}"

                // Tampilkan diagnosis date
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                inputFormat.timeZone = TimeZone.getTimeZone("UTC")

                val outputFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
                outputFormat.timeZone = TimeZone.getTimeZone("Asia/Jakarta")

                val formattedDate = try {
                    val date = inputFormat.parse(history.diagnosisDate ?: "")
                    date?.let {
                        // Tambahkan 7 jam untuk WIB
                        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Jakarta"))
                        calendar.time = it
                        calendar.add(Calendar.HOUR_OF_DAY, 0)

                        outputFormat.format(calendar.time)
                    } ?: "No Date"
                } catch (e: Exception) {
                    "Invalid Date"
                }

                // Tampilkan diagnosis date yang sudah diformat
                histTvItemName3.text = "Date: $formattedDate WIB"

                // Tampilkan gambar yang di-upload
                Glide.with(itemView.context)
                    .load(history.imageUrl)
                    .into(histImgItemPhoto)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HistoryRowBinding.inflate(
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryItem>() {
            override fun areItemsTheSame(
                oldItem: HistoryItem,
                newItem: HistoryItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: HistoryItem,
                newItem: HistoryItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}