package com.luthfi.covidout.ui.explore

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.luthfi.covidout.R
import com.luthfi.covidout.data.model.CountryCase
import com.luthfi.covidout.data.model.CountryCaseResponse
import com.luthfi.covidout.data.model.GlobalCase
import com.luthfi.covidout.utils.*
import kotlinx.android.synthetic.main.fragment_explore.*


class ExploreFragment : Fragment() {

    private lateinit var viewModel: ExploreViewModel
    private lateinit var adapter: CountryCaseAdapter
    private val caseList = mutableListOf<CountryCase>()
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        setUpRecycler()

        nestedLayout.gone()
        progressBar.visible()

        viewModel = ViewModelProvider(this).get(ExploreViewModel::class.java)
        viewModel.allCountryCase?.observe(viewLifecycleOwner, countryCaseObserver)
    }

    @SuppressLint("SetTextI18n")
    private val countryCaseObserver = Observer<CountryCaseResponse> {
        caseList.clear()
        caseList.addAll(it.countries.sortedByDescending { case -> case.totalConfirmed })

        adapter.setCaseData(caseList)
        showGlobalData(it.global)

        progressBar.gone()
        nestedLayout.visible()

        tvLastUpdate.text = "Update Terakhir : " + formatUTCDate(it.countries[0].date, "dd MMMM yyyy HH:mm")
    }

    private fun setUpRecycler() {
        adapter = CountryCaseAdapter(caseList)
        rvCountryCase.layoutManager = GridLayoutManager(context, 2)
        rvCountryCase.adapter = adapter
    }

    @SuppressLint("SetTextI18n")
    private fun showGlobalData(globalCase: GlobalCase) {
        with(globalCase) {
            val activeCase =
                globalCase.totalConfirmed - globalCase.totalDeaths - globalCase.totalRecovered
            val recoverRate = roundNumber((totalRecovered.toDouble() / totalConfirmed) * 100)
            val deathRate = roundNumber((totalDeaths.toDouble() / totalConfirmed) * 100)
            val activeRate = roundNumber((activeCase.toDouble() / totalConfirmed) * 100)

            tvCaseTotal.text = formatNumber(globalCase.totalConfirmed)
            tvRecover.text = formatNumber(globalCase.totalRecovered)
            tvDeath.text = formatNumber(globalCase.totalDeaths)
            tvActiveCase.text = formatNumber(activeCase)

            tvLabelConfirmed.append(" (+${globalCase.newConfirmed})")
            tvLabelRecover.append(" (+${globalCase.newRecovered})")
            tvLabelDeath.append(" (+${globalCase.newDeaths})")

            tvActiveRate.text = "Kasus Aktif : $activeRate%"
            tvRecoverRate.text = "Sembuh : $recoverRate%"
            tvDeathRate.text = "Kematian : $deathRate%"
        }
    }

    private val queryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String): Boolean {
            val filteredCountry = caseList.filter { it.country.contains(newText, true) }
            adapter.setCaseData(filteredCountry)
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
                adapter.setCaseData(caseList)
            }
        }

        super.onCreateOptionsMenu(menu, inflater)
    }
}
