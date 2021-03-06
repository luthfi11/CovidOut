package com.luthfi.covidout.data.api

import com.luthfi.covidout.data.model.*
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET("/api/reports?iso=IDN")
    fun getIndonesiaCase(): Call<IndonesiaCaseResponse>

    @GET("/country/indonesia/status/confirmed")
    fun getIndonesiaDevCase(): Call<List<CaseDevelopment>>

    @GET("/indonesia/provinsi")
    fun getAllProvinceCase(): Call<List<ProvinceResponse>>

    @GET("/summary")
    fun getAllCountryCase(): Call<CountryCaseResponse>

    @GET("/total/country/{slug}/status/confirmed")
    fun getCountryDevCase(@Path("slug") slug: String): Call<List<CaseDevelopment>>

    @GET("/v2/top-headlines?country=id&category=health&apiKey=83a5a45360934b70a67749ad00510fe2")
    fun getNews(): Call<NewsResponse>
}