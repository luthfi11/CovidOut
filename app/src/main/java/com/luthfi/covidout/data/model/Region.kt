package com.luthfi.covidout.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Region (
    var name: String,
    var webUrl: String,
    var logo: String
): Parcelable