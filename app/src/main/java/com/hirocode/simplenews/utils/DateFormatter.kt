package com.hirocode.simplenews.utils

import android.os.Build
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateFormatter {
    fun formatDate(currentDateString: String, targetTimeZone: String): String {
        val instant = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Instant.parse(currentDateString)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy | HH:mm")
            .withZone(ZoneId.of(targetTimeZone))
        return formatter.format(instant)
    }
}