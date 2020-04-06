package com.luthfi.covidout.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.luthfi.covidout.data.model.News
import com.luthfi.covidout.data.repository.DataRepository

class NewsViewModel: ViewModel() {

    private val repo = DataRepository()

    val allNews: LiveData<List<News>>? = repo.getNews()
}