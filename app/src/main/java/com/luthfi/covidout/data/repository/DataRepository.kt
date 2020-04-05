package com.luthfi.covidout.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.luthfi.covidout.data.api.APIClient
import com.luthfi.covidout.data.api.APIService
import com.luthfi.covidout.data.model.CountryCase
import com.luthfi.covidout.data.model.IndonesiaCase
import com.luthfi.covidout.data.model.ProvinceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataRepository {

    private val apiKawalCorona = APIClient.getClient("https://api.kawalcorona.com").create(APIService::class.java)
    private val apiCovid19 = APIClient.getClient("https://api.covid19api.com/").create(APIService::class.java)

    fun getIndonesiaCase(): MutableLiveData<IndonesiaCase>? {
        val case = MutableLiveData<IndonesiaCase>()
        apiKawalCorona.getIndonesiaCase().enqueue(object : Callback<List<IndonesiaCase>> {
            override fun onFailure(call: Call<List<IndonesiaCase>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<List<IndonesiaCase>>, response: Response<List<IndonesiaCase>>) {
                Log.d("ResponseAPIKawalCorona", response.body().toString())
                response.body()?.let {
                    case.postValue(it[0])
                }
            }
        })

        return case
    }

    fun getIndonesiaDevCase(): MutableLiveData<List<CountryCase>>? {
        val case = MutableLiveData<List<CountryCase>>()
        apiCovid19.getIndonesiaDevCase().enqueue(object : Callback<List<CountryCase>> {
            override fun onFailure(call: Call<List<CountryCase>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<CountryCase>>,
                response: Response<List<CountryCase>>
            ) {
                response.body()?.let {
                    case.postValue(it)
                }
            }

        })

        return case
    }

    fun getAllProvinceCase(): MutableLiveData<List<ProvinceResponse>>? {
        val case = MutableLiveData<List<ProvinceResponse>>()
        apiKawalCorona.getAllProvinceCase().enqueue(object : Callback<List<ProvinceResponse>> {
            override fun onFailure(call: Call<List<ProvinceResponse>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<List<ProvinceResponse>>,
                response: Response<List<ProvinceResponse>>
            ) {
                Log.d("ResponseAPIKawalCorona", response.body().toString())
                case.postValue(response.body())
            }
        })

        return case
    }

}