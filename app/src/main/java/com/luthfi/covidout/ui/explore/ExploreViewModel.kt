package com.luthfi.covidout.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.luthfi.covidout.data.model.CaseDevelopment
import com.luthfi.covidout.data.model.CountryCaseResponse
import com.luthfi.covidout.data.repository.DataRepository

class ExploreViewModel : ViewModel() {

    private val repo = DataRepository()

    val allCountryCase: LiveData<CountryCaseResponse>? = repo.getAllCountryCase()

    fun getCaseDevelopment(slug: String): LiveData<List<CaseDevelopment>>? = repo.getCountryDevCase(slug)
}