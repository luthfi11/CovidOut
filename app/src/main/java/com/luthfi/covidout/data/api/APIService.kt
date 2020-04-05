package com.luthfi.covidout.data.api

import com.luthfi.covidout.data.model.CountryCase
import com.luthfi.covidout.data.model.IndonesiaCase
import com.luthfi.covidout.data.model.ProvinceResponse
import retrofit2.Call
import retrofit2.http.GET

interface APIService {

    @GET("/indonesia/")
    fun getIndonesiaCase(): Call<List<IndonesiaCase>>

    @GET("/country/indonesia/status/confirmed")
    fun getIndonesiaDevCase(): Call<List<CountryCase>>

    @GET("/indonesia/provinsi/")
    fun getAllProvinceCase(): Call<List<ProvinceResponse>>

}