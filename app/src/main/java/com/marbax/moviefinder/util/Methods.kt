package com.marbax.moviefinder.util

import java.text.SimpleDateFormat
import java.util.*

class Methods {

    // static methods
    companion object {

        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        fun dateStringToCalendar(date: String): Calendar {
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
            return if (date.isNotBlank()) {
                val cal = Calendar.getInstance()
                cal.time = format.parse(date)
                cal
            } else {
                Calendar.getInstance()
            }
        }

        // yyyy-MM-dd
        fun formatDateToString(year: Int, month: Int, dayOfMonth: Int): String {
            val monthStr = String.format("%02d", month + 1)
            val dayStr = String.format("%02d", dayOfMonth)
            return "${year}-${monthStr}-${dayStr}"
        }

        // yyyy-MM-dd
        fun formatDateToString(calendar: Calendar): String {
            val monthStr = String.format("%02d", calendar[Calendar.MONTH] + 1)
            val dayStr = String.format("%02d", calendar[Calendar.DAY_OF_MONTH])
            return "${calendar[Calendar.YEAR]}-${monthStr}-${dayStr}"
        }

    }
}