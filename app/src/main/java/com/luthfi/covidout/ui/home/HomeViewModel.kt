package com.luthfi.covidout.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.luthfi.covidout.data.model.CountryCase
import com.luthfi.covidout.data.model.IndonesiaCase
import com.luthfi.covidout.data.model.ProvinceResponse
import com.luthfi.covidout.data.repository.DataRepository

class HomeViewModel : ViewModel() {

    private val repo = DataRepository()

    val indonesiaCase: LiveData<IndonesiaCase>? = repo.getIndonesiaCase()
    val indonesiaDevCase: LiveData<List<CountryCase>>? = repo.getIndonesiaDevCase()
    val allProvinceCase: LiveData<List<ProvinceResponse>>? = repo.getAllProvinceCase()
}