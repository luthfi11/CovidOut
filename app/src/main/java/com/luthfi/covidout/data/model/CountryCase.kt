package com.luthfi.covidout.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GlobalCase (
    @SerializedName("NewConfirmed")
    var newConfirmed: Int,
    @SerializedName("TotalConfirmed")
    var totalConfirmed: Int,
    @SerializedName("NewDeaths")
    var newDeaths: Int,
    @SerializedName("TotalDeaths")
    var totalDeaths: Int,
    @SerializedName("NewRecovered")
    var newRecovered: Int,
    @SerializedName("TotalRecovered")
    var totalRecovered: Int
): Parcelable

@Parcelize
data class CountryCase(
    @SerializedName("Country")
    var country: String,
    @SerializedName("CountryCode")
    var countryCode: String,
    @SerializedName("Slug")
    var slug: String,
    @SerializedName("NewConfirmed")
    var newConfirmed: Int,
    @SerializedName("TotalConfirmed")
    var totalConfirmed: Int,
    @SerializedName("NewDeaths")
    var newDeaths: Int,
    @SerializedName("TotalDeaths")
    var totalDeaths: Int,
    @SerializedName("NewRecovered")
    var newRecovered: Int,
    @SerializedName("TotalRecovered")
    var totalRecovered: Int,
    @SerializedName("Date")
    var date: String
): Parcelable

data class CountryCaseResponse(
    @SerializedName("Countries")
    var countries: List<CountryCase>,
    @SerializedName("Global")
    var global: GlobalCase
)