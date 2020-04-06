package com.luthfi.covidout.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.luthfi.covidout.data.model.CountryCase
import com.luthfi.covidout.data.model.IndonesiaCase
import com.luthfi.covidout.data.repository.DataRepository

class ExploreViewModel : ViewModel() {

    private val repo = DataRepository()

    val allCountryCase: LiveData<List<CountryCase>>? = repo.getAllCountryCase()
}