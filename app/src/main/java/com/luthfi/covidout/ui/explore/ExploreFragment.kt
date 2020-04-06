package com.luthfi.covidout.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.luthfi.covidout.R
import com.luthfi.covidout.data.model.CountryCase
import kotlinx.android.synthetic.main.fragment_explore.*

class ExploreFragment : Fragment() {

    private lateinit var viewModel : ExploreViewModel
    private lateinit var adapter: CountryCaseAdapter
    private val caseList = mutableListOf<CountryCase>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
        viewModel = ViewModelProvider(this).get(ExploreViewModel::class.java)
        viewModel.allCountryCase?.observe(viewLifecycleOwner, countryCaseObserver)
    }

    private val countryCaseObserver = Observer<List<CountryCase>> {
        caseList.clear()
        caseList.addAll(it)
        adapter.notifyDataSetChanged()
    }

    private fun setUpRecycler() {
        adapter = CountryCaseAdapter(caseList)
        rvCountryCase.layoutManager = GridLayoutManager(context, 2)
        rvCountryCase.adapter = adapter
    }
}
