package com.luthfi.covidout.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CaseDevelopment (
    @SerializedName("Country")
    var country: String,
    @SerializedName("Lat")
    var lat: Double,
    @SerializedName("Lon")
    var lon: Double,
    @SerializedName("Date")
    var date: String,
    @SerializedName("Cases")
    var cases: Int
): Parcelable
