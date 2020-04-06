package com.luthfi.covidout.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(
    var id: Int,
    var title: String,
    var banner: Int,
    var content: String
): Parcelable