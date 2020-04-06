package com.luthfi.covidout.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IndonesiaCase(
    @SerializedName("last_update")
    var date: String,
    var confirmed: Int,
    var recovered: Int,
    var deaths: Int,
    @SerializedName("confirmed_diff")
    var confirmedDiff: Int,
    @SerializedName("recovered_diff")
    var recoveredDiff: Int,
    @SerializedName("deaths_diff")
    var deathsDiff: Int,
    @SerializedName("fatality_rate")
    var fatalityRate: Double,
    var active: Int
): Parcelable

data class IndonesiaCaseResponse (
    var data: List<IndonesiaCase>
)