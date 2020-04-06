package com.luthfi.covidout.ui.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.luthfi.covidout.R
import com.luthfi.covidout.utils.cityData
import com.luthfi.covidout.utils.provinceData
import kotlinx.android.synthetic.main.fragment_info.*

class InfoFragment : Fragment() {

    private lateinit var provinceAdapter: RegionAdapter
    private lateinit var cityAdapter: RegionAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
    }

    private fun setUpRecycler() {
        provinceAdapter = RegionAdapter(provinceData)
        rvProvince.layoutManager = LinearLayoutManager(context)
        rvProvince.adapter = provinceAdapter

        cityAdapter = RegionAdapter(cityData)
        rvCity.layoutManager = LinearLayoutManager(context)
        rvCity.adapter = cityAdapter
    }
}
