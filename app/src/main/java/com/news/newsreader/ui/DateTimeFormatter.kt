package com.news.newsreader.ui

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateTimeUtils {

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatArticleDate(
        isoDate: String,
        zoneId: ZoneId = ZoneId.of("America/New_York")
    ) : String {
        val instant = Instant.parse(isoDate)

        val formatter = DateTimeFormatter
            .ofPattern("MMMM d, yyyy | h:mm a z")
            .withLocale(Locale.US)
            .withZone(zoneId)

        return formatter.format(instant)
    }
}