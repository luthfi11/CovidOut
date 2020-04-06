package com.luthfi.covidout.utils

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun formatUTCDate(date: String, pattern: String): String {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())

    return sdf.format(format.parse(date))
}

fun formatDate(date: String): String {
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    val sdf = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.getDefault())
    return sdf.format(format.parse(date))
}

fun roundNumber(number: Double): String {
    return BigDecimal(number).setScale(2, RoundingMode.HALF_EVEN).toString()
}

fun formatNumber(number: Int) : String {
    return NumberFormat.getNumberInstance(Locale.getDefault()).format(number).toString()
}