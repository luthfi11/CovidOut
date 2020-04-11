package com.luthfi.covidout.ui.explore

import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.Dialog
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.data.Set
import com.bumptech.glide.Glide
import com.luthfi.covidout.R
import com.luthfi.covidout.data.model.CountryCase
import com.luthfi.covidout.data.model.CountryCaseResponse
import com.luthfi.covidout.data.model.GlobalCase
import com.luthfi.covidout.utils.*
import kotlinx.android.synthetic.main.fragment_explore.*
import kotlinx.android.synthetic.main.layout_country.view.*
import java.util.*

@SuppressLint("SetTextI18n")
class ExploreFragment : Fragment() {

    private lateinit var viewModel: ExploreViewModel
    private lateinit var adapter: CountryCaseAdapter
    private val caseList = mutableListOf<CountryCase>()
    private lateinit var searchView: SearchView
    private var newCaseAverage = ""

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

    private val countryCaseObserver = Observer<CountryCaseResponse> {
        caseList.clear()
        caseList.addAll(it.countries
            .sortedByDescending { case -> case.totalConfirmed }
            .filter { case -> case.totalConfirmed > 0 }
        )

        adapter.setCaseData(caseList)
        adapter.setOnCountryClick(onCountryCallback)
        showGlobalData(it.global)

        progressBar.gone()
        nestedLayout.visible()

        tvLastUpdate.text =
            "Update Terakhir : " + formatUTCDate(it.countries[0].date, "dd MMMM yyyy HH:mm")
    }

    private val onCountryCallback = object : CountryCaseAdapter.OnCountryCallback {
        override fun onCountrySelected(countryCase: CountryCase) {
            val flagsUrl =
                "https://raw.githubusercontent.com/hjnilsson/country-flags/master/png250px/${countryCase.countryCode.toLowerCase(
                    Locale.ROOT
                )}.png"
            showProvinceDialog(countryCase, flagsUrl)
        }
    }

    private fun setUpRecycler() {
        adapter = CountryCaseAdapter(caseList)
        rvCountryCase.layoutManager = GridLayoutManager(context, 2)
        rvCountryCase.adapter = adapter
    }

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

    @SuppressLint("InflateParams", "SetTextI18n")
    private fun showProvinceDialog(countryCase: CountryCase, flag: String?) {
        val countryDialog =
            LayoutInflater.from(context).inflate(R.layout.layout_country, null)

        val dialog = Dialog(activity!!).apply {
            setContentView(countryDialog)
            window?.setBackgroundDrawableResource(android.R.color.white)
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT
            );
        }

        dialog.show()

        val activeCase =
            countryCase.totalConfirmed - countryCase.totalRecovered - countryCase.totalDeaths
        val activeRate = roundNumber(activeCase.toDouble() / countryCase.totalConfirmed * 100)
        val recoverRate =
            roundNumber((countryCase.totalRecovered.toDouble() / countryCase.totalConfirmed) * 100)
        val deathRate =
            roundNumber((countryCase.totalDeaths.toDouble() / countryCase.totalConfirmed) * 100)

        val caseDevList = arrayListOf<DataEntry>()
        val casePerDayList = arrayListOf<DataEntry>()
        viewModel.getCaseDevelopment(countryCase.slug)
            ?.observe(viewLifecycleOwner, Observer { case ->
                var caseAverage = 0
                for (i in case.indices) {
                    val caseCount: Int = if (i != 0) case[i].cases - case[i - 1].cases
                    else case[i].cases

                    caseDevList.add(
                        ValueDataEntry(
                            formatUTCDate(case[i].date, "d-MMM"),
                            case[i].cases
                        )
                    )

                    casePerDayList.add(
                        ValueDataEntry(
                            formatUTCDate(case[i].date, "d-MMM"),
                            caseCount
                        )
                    )

                    caseAverage += caseCount
                }

                caseAverage = (caseAverage.div(case.size))

                countryDialog.tvCountryAverageCase.text = caseAverage.toString()
                setUpLineChart(countryDialog.chartDevelopment, caseDevList)
                setUpBarChart(countryDialog.chartPerDay, casePerDayList)
            })

        with(countryDialog) {
            if (flag != null)
                Glide.with(context).load(flag).into(imgFlag)

            tvCountryName.text = countryCase.country
            tvCountryCaseTotal.text =
                "${formatNumber(countryCase.totalConfirmed)} (+${formatNumber(countryCase.newConfirmed)})"
            tvCountryRecoverTotal.text =
                "${formatNumber(countryCase.totalRecovered)} (+${formatNumber(countryCase.newRecovered)})"
            tvCountryDeathTotal.text =
                "${formatNumber(countryCase.totalDeaths)} (+${formatNumber(countryCase.newDeaths)})"
            tvCountryActiveCase.text = "${formatNumber(activeCase)} ($activeRate%)"
            tvCountryRecoverRate.text = "$recoverRate%"
            tvCountryDeathRate.text = "$deathRate%"

            tvClose.setOnClickListener { dialog.dismiss() }
        }
    }

    private fun setUpLineChart(chartDevelopment: AnyChartView, caseDevList: List<DataEntry>) {
        APIlib.getInstance().setActiveAnyChartView(chartDevelopment)
        val cartesian = AnyChart.line().apply {
            animation(true)
            xScroller(true)
            xScroller().position("beforeAxes")
            xScroller().allowRangeChange(false)
            xZoom().setToPointsCount(10, true, null)
            legend().enabled(true)
        }

        val set = Set.instantiate().apply {
            data(caseDevList)
        }

        val caseMapping = set.mapAs("{ x: 'date', value: 'case' }")
        val chart = cartesian.line(caseMapping)

        chart.name("Kasus Positif")
        chart.color("#2979FF")
        chartDevelopment.setChart(cartesian)
    }

    private fun setUpBarChart(chartCasePerDay: AnyChartView, casePerDayList: List<DataEntry>) {
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
        chart.color("#2979FF")
        chartCasePerDay.setChart(cartesian)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView = searchItem.actionView as SearchView
        searchView.isIconified = false
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
