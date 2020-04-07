package com.luthfi.covidout.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.luthfi.covidout.data.api.APIClient
import com.luthfi.covidout.data.api.APIService
import com.luthfi.covidout.data.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataRepository {

    private val urlCovidAPI = "https://covid-api.com/"
    private val urlKawalCorona = "https://api.kawalcorona.com/"
    private val urlCovid19 = "https://api.covid19api.com/"
    private val urlNews = "https://newsapi.org/"

    private fun apiClient(baseUrl: String): APIService {
        return APIClient.getClient(baseUrl).create(APIService::class.java)
    }

    fun getIndonesiaCase(): MutableLiveData<IndonesiaCase>? {
        val case = MutableLiveData<IndonesiaCase>()
        apiClient(urlCovidAPI).getIndonesiaCase().enqueue(object : Callback<IndonesiaCaseResponse> {
            override fun onFailure(call: Call<IndonesiaCaseResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<IndonesiaCaseResponse>,
                response: Response<IndonesiaCaseResponse>
            ) {
                Log.d("ResponseCovidAPI", response.body().toString())
                response.body()?.let {
                    case.postValue(it.data[0])
                }
            }
        })

        return case
    }

    fun getIndonesiaDevCase(): MutableLiveData<List<CaseDevelopment>>? {
        val case = MutableLiveData<List<CaseDevelopment>>()
        apiClient(urlCovid19).getIndonesiaDevCase().enqueue(object : Callback<List<CaseDevelopment>> {
            override fun onFailure(call: Call<List<CaseDevelopment>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<CaseDevelopment>>,
                response: Response<List<CaseDevelopment>>
            ) {
                response.body()?.let {
                    Log.d("ResponseCovid19", response.body().toString())
                    val filteredCase = it.filter { data -> data.cases != 0 }
                    case.postValue(filteredCase)
                }
            }

        })

        return case
    }

    fun getAllProvinceCase(): MutableLiveData<List<ProvinceResponse>>? {
        val case = MutableLiveData<List<ProvinceResponse>>()
        apiClient(urlKawalCorona).getAllProvinceCase().enqueue(object : Callback<List<ProvinceResponse>> {
            override fun onFailure(call: Call<List<ProvinceResponse>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<ProvinceResponse>>,
                response: Response<List<ProvinceResponse>>
            ) {
                Log.d("ResponseKawalCorona", response.body().toString())
                case.postValue(response.body())
            }
        })

        return case
    }

    fun getAllCountryCase(): MutableLiveData<CountryCaseResponse>? {
        val cases = MutableLiveData<CountryCaseResponse>()
        apiClient(urlCovid19).getAllCountryCase().enqueue(object : Callback<CountryCaseResponse> {
            override fun onFailure(call: Call<CountryCaseResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<CountryCaseResponse>, response: Response<CountryCaseResponse>) {
                Log.d("ResponseCovid19", response.body().toString())
                response.body()?.let {
                    cases.postValue(it)
                }
            }

        })

        return cases
    }

    fun getNews(): MutableLiveData<List<News>>? {
        val news = MutableLiveData<List<News>>()
        apiClient(urlNews).getNews().enqueue(object : Callback<NewsResponse> {
            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                Log.d("ResponseNewsAPI", response.body().toString())
                news.postValue(response.body()?.articles)
            }
        })

        return news
    }

}