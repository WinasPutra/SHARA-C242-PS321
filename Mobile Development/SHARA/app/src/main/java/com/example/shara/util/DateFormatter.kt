package com.example.shara.util

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

object DateFormatter {
    fun formatDate(currentDate: String): String {
        val currentFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val targetFormat = SimpleDateFormat("dd MMM yyyy | HH:mm", Locale.getDefault())

        // Set timezone ke WIB (Waktu Indonesia Barat)
        currentFormat.timeZone = TimeZone.getTimeZone("Asia/Jakarta")
        targetFormat.timeZone = TimeZone.getTimeZone("Asia/Jakarta")

        return try {
            val date = currentFormat.parse(currentDate)
            date?.let {
                // Konversi ke zona waktu Indonesia
                targetFormat.format(it)
            } ?: "Date not available"
        } catch (ex: ParseException) {
            "Invalid date"
        }
    }

    fun formatDateWib(inputDate: String): String {
        return try {
            // Format input dari API
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")

            // Format output yang diinginkan
            val outputFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
            outputFormat.timeZone = TimeZone.getTimeZone("Asia/Jakarta")

            // Parse dan format ulang
            val date = inputFormat.parse(inputDate)
            date?.let { outputFormat.format(it) } ?: "No Date"
        } catch (e: Exception) {
            "Invalid Date"
        }
    }

}

// Contoh penggunaan zona waktu Indonesia
object IndonesianTimeZones {
    const val WESTERN = "Asia/Jakarta"   // WIB (Waktu Indonesia Barat)
    const val CENTRAL = "Asia/Makassar"  // WITA (Waktu Indonesia Tengah)
    const val EASTERN = "Asia/Jayapura"  // WIT (Waktu Indonesia Timur)
}