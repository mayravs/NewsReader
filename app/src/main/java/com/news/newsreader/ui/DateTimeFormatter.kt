package com.news.newsreader.ui

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateTimeUtils {

    fun formatArticleDate(
        isoDate: String
    ) : String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            val date = inputFormat.parse(isoDate) ?: return isoDate
            
            val outputFormat = SimpleDateFormat("MMMM d, yyyy | h:mm a", Locale.US).apply {
                timeZone = TimeZone.getTimeZone("America/New_York")
            }
            outputFormat.format(date)
        } catch (e: Exception) {
            isoDate
        }
    }
}