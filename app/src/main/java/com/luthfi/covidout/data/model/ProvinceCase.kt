package com.luthfi.covidout.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProvinceCase(
    @SerializedName("FID")
    var id: Int,
    @SerializedName("Kode_Provi")
    var provinceCode: Int,
    @SerializedName("Provinsi")
    var province: String,
    @SerializedName("Kasus_Posi")
    var positiveCase: Int,
    @SerializedName("Kasus_Semb")
    var recoverCase: Int,
    @SerializedName("Kasus_Meni")
    var deathCase: Int
) : Parcelable

data class ProvinceResponse(
    var attributes: ProvinceCase
)