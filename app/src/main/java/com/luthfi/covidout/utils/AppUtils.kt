package com.luthfi.covidout.utils

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*

fun formatDate(date: String, pattern: String): String {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())

    return sdf.format(format.parse(date))
}

fun roundNumber(number: Double): BigDecimal {
    return BigDecimal(number).setScale(2, RoundingMode.HALF_EVEN)
}