package com.droidcon.adaptiveinbox.utils

import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils
import java.util.Calendar
import java.util.Date
import kotlin.math.absoluteValue

fun String.mimeTypeToFormattedString(): String {
    // Convert MIME type to a more readable format
    return when (this) {
        "application/pdf" -> "PDF Document"
        "image/jpeg" -> "JPEG Image"
        "image/png" -> "PNG Image"
        "text/plain" -> "Plain Text"
        else -> this
    }
}

fun Long.kbToMbFormattedString(): String {
    // Convert bytes to megabytes
    return String.format("%.2f MB", this / 1024f / 1024f)
}

@ColorInt
fun String.toHslColor(saturation: Float = 0.5f, lightness: Float = 0.2f): Int {
    val hue = fold(0) { acc, char -> char.code + acc * 37 } % 360
    return ColorUtils.HSLToColor(floatArrayOf(hue.absoluteValue.toFloat(), saturation, lightness))
}

fun Long.toDate(): Date {
    // Convert milliseconds to Date
    return Date(this)
}

fun Date.toFormattedString(): String {
    // Format and show time in 12-hour format like 11:58 PM if time is less than 24 hours old
    // or show date in format like 24 June if time is older than 24 hours but in the current year
    // or show date in format like 24 June 2023 if time is older not in the current year

    val currentCalendar = Calendar.getInstance()
    val dateCalendar = Calendar.getInstance().apply {
        time = this@toFormattedString
    }

    val isSameYear = currentCalendar.get(Calendar.YEAR) == dateCalendar.get(Calendar.YEAR)
    val isSameDay = currentCalendar.get(Calendar.DAY_OF_YEAR) == dateCalendar.get(Calendar.DAY_OF_YEAR)

    if (isSameDay) {
        val hour = dateCalendar.get(Calendar.HOUR)
        val minute = dateCalendar.get(Calendar.MINUTE)
        val amPm = if (dateCalendar.get(Calendar.AM_PM) == Calendar.AM) "AM" else "PM"
        return String.format("%02d:%02d %s", hour, minute, amPm)
    } else if (isSameYear) {
        val month = dateCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, java.util.Locale.getDefault())
        val day = dateCalendar.get(Calendar.DAY_OF_MONTH)
        return "$day $month"
    } else {
        val month = dateCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, java.util.Locale.getDefault())
        val day = dateCalendar.get(Calendar.DAY_OF_MONTH)
        val year = dateCalendar.get(Calendar.YEAR)
        return "$day $month $year"
    }
}