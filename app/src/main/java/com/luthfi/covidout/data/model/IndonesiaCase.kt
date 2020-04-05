package com.luthfi.covidout.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IndonesiaCase(
    var name: String,
    @SerializedName("positif")
    var positive: String,
    @SerializedName("sembuh")
    var recover: String,
    @SerializedName("meninggal")
    var death: String
): Parcelable


/*
"data": [
{
"date": "2020-04-02",
"confirmed": 1790,
"deaths": 170,
"recovered": 112,
"confirmed_diff": 113,
"deaths_diff": 13,
"recovered_diff": 9,
"last_update": "2020-04-02 23:25:14",
"active": 1508,
"active_diff": 91,
"fatality_rate": 0.095,
}
]
 */