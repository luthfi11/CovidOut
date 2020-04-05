package com.luthfi.covidout.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.data.Set
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.luthfi.covidout.R
import com.luthfi.covidout.data.model.CountryCase
import com.luthfi.covidout.data.model.IndonesiaCase
import com.luthfi.covidout.data.model.ProvinceResponse
import com.luthfi.covidout.utils.CustomDataEntry
import com.luthfi.covidout.utils.formatDate
import com.luthfi.covidout.utils.roundNumber
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*


@SuppressLint("SetTextI18n")
class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: ProvinceCaseAdapter
    private val caseList = mutableListOf<ProvinceResponse>()
    private val caseDevList = arrayListOf<DataEntry>()
    private val casePerDayList = arrayListOf<DataEntry>()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var location: Location? = null
    private var locationRequest: LocationRequest? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecycler()

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.indonesiaCase?.observe(viewLifecycleOwner, indonesiaCaseObserver)
        viewModel.indonesiaDevCase?.observe(viewLifecycleOwner, indonesiaDevObserver)
        viewModel.allProvinceCase?.observe(viewLifecycleOwner, provinceCaseObserver)

        btnRefreshLocation.setOnClickListener { getLastLocation() }
    }

    private val indonesiaCaseObserver = Observer<IndonesiaCase> {
        val positiveCase = it.positive.replace(",","").toInt()
        val recoverRate = roundNumber((it.recover.toDouble() / positiveCase) * 100)
        val deathRate = roundNumber((it.death.toDouble() / positiveCase) * 100)

        tvCaseTotalID.text = it.positive
        tvRecoverID.text = it.recover
        tvDeathID.text = it.death
        tvRecoverRateID.text = "Persentase : $recoverRate%"
        tvDeathRateID.text = "Persentase : $deathRate%"
    }

    private val indonesiaDevObserver = Observer<List<CountryCase>> { countryCase ->
        tvLastUpdate.text = "Update Terakhir : "+formatDate(countryCase.last().date, "dd MMMM yyyy")

        for (i in countryCase.indices) {
            val caseCount: Int = if (i != 0) countryCase[i].cases - countryCase[i-1].cases
            else countryCase[i].cases

            caseDevList.add(CustomDataEntry(formatDate(countryCase[i].date, "d-MMM"), countryCase[i].cases))
            casePerDayList.add(CustomDataEntry(formatDate(countryCase[i].date, "d-MMM"), caseCount))
        }

        setUpLineChart()
        setUpBarChart()
    }

    private val provinceCaseObserver = Observer<List<ProvinceResponse>> {
        caseList.clear()
        caseList.addAll(it)
        adapter.notifyDataSetChanged()
    }

    private fun setUpLineChart() {
        APIlib.getInstance().setActiveAnyChartView(chartDevelopment)
        val cartesian = AnyChart.line().apply {
            animation(true)
            xScroller(true)
            xScroller().position("beforeAxes")
            legend().enabled(true)
        }

        val set = Set.instantiate().apply {
            data(caseDevList)
        }

        val caseMapping = set.mapAs("{ x: 'date', value: 'case' }")
        val chart = cartesian.line(caseMapping)

        chart.name("Kasus Positif")
        chartDevelopment.setChart(cartesian)
    }

    private fun setUpBarChart() {
        APIlib.getInstance().setActiveAnyChartView(chartCasePerDay)
        val cartesian = AnyChart.column().apply {
            animation(true)
            xScroller(true)
            xScroller().position("beforeAxes")
            xScroller().allowRangeChange(false)
            xZoom().setToPointsCount(6, true, null)
            legend().enabled(true)
        }

        val set = Set.instantiate().apply {
            data(casePerDayList)
        }

        val caseMapping = set.mapAs("{ x: 'date', value: 'case' }")
        val chart = cartesian.column(caseMapping)

        chart.name("Kasus Positif")
        chartCasePerDay.setChart(cartesian)
    }

    private fun setUpRecycler() {
        adapter = ProvinceCaseAdapter(caseList)
        rvCaseProvince.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)
        rvCaseProvince.adapter = adapter
    }

    // Location Request
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            getLastLocation()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {
            if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            } else {
                Toast.makeText(context, "Lokasi Tidak Diizinkan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onGetLastLocation(location: Location?) {
        if (location != null) {
            this.location = location
            try {
                val geo = Geocoder(context, Locale.getDefault())
                val addresses: List<Address> = geo.getFromLocation(location.latitude, location.longitude, 1)
                if (addresses.isEmpty()) tvLocation.text = "Menunggu Loakasi Anda Diaktifkan"
                else {
                    if (addresses.isNotEmpty()) {
                        tvLocation.text = addresses[0].featureName.toString() + ", " + addresses[0]
                            .locality + ", " + addresses[0]
                            .adminArea + ", " + addresses[0].countryName
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Terjadi Kesalahan, Mohon Coba Lagi.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun requestLocationPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity!!,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                requestLocationPermission()
            } else {
                requestLocationPermission()
            }
        } else {
            if (locationRequest == null) {
                locationRequest = LocationRequest.create()?.apply {
                    interval = 10000
                    fastestInterval = 5000
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                }
                if (locationRequest != null) {
                    requestTurnOnLocation()
                }
            } else {
                requestTurnOnLocation()
            }
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            for (location in locationResult.locations) {
                onGetLastLocation(location)
                break
            }
            fusedLocationClient.removeLocationUpdates(this)
        }
    }

    @SuppressLint("MissingPermission")
    fun requestTurnOnLocation() {
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest!!)
        val client: SettingsClient = LocationServices.getSettingsClient(activity!!)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                try {
                    startIntentSenderForResult(exception.resolution.intentSender, 1, null, 0, 0, 0, null)
                } catch (sendEx: IntentSender.SendIntentException) {
                    sendEx.printStackTrace()
                }
            }
        }
    }
}
