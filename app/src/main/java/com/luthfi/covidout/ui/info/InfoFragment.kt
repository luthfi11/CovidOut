package com.luthfi.covidout.ui.info

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.luthfi.covidout.R
import com.luthfi.covidout.utils.visible
import com.luthfi.covidout.utils.cityData
import com.luthfi.covidout.utils.gone
import com.luthfi.covidout.utils.provinceData
import kotlinx.android.synthetic.main.fragment_info.*


class InfoFragment : Fragment() {

    private lateinit var provinceAdapter: RegionAdapter
    private lateinit var cityAdapter: RegionAdapter
    private lateinit var searchView: SearchView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
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

    private val queryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String): Boolean {
            val provinceFiltered = provinceData.filter { it.name.contains(newText, true) }
            val cityFiltered = cityData.filter { it.name.contains(newText, true) }

            provinceAdapter.setRegionData(provinceFiltered)
            cityAdapter.setRegionData(cityFiltered)

            if (provinceFiltered.isEmpty()) tvProvinceNotFound.visible()
            else tvProvinceNotFound.gone()

            if (cityFiltered.isEmpty()) tvCityNotFound.visible()
            else tvCityNotFound.gone()

            return true
        }

        override fun onQueryTextSubmit(query: String): Boolean {
            return true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView = searchItem.actionView as SearchView
        searchView.isIconifiedByDefault = false
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.setOnQueryTextListener(queryTextListener)
        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                searchView.setQuery("", false)
                cityAdapter.setRegionData(cityData)
                provinceAdapter.setRegionData(provinceData)
            }
        }
        super.onCreateOptionsMenu(menu, inflater)
    }
}
