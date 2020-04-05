package com.luthfi.covidout.utils

import com.anychart.chart.common.dataentry.ValueDataEntry

class CustomDataEntry internal constructor(
    date: String?,
    case: Number?
) :
    ValueDataEntry(date, case)