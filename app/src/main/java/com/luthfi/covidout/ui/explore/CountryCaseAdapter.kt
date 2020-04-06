package com.luthfi.covidout.ui.explore

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.luthfi.covidout.R
import com.luthfi.covidout.data.model.CountryCase
import kotlinx.android.synthetic.main.item_case_by_province.view.*

class CountryCaseAdapter(private val countryCaseList: List<CountryCase>): RecyclerView.Adapter<CountryCaseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_case_by_province, parent, false))
    }

    override fun getItemCount(): Int {
        return countryCaseList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(countryCaseList[position])
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bindItem(countryCase: CountryCase) {
            with(itemView) {
                tvProvince.text = countryCase.country
                tvCaseTotal.text = countryCase.totalConfirmed.toString()
                tvRecoverTotal.text = countryCase.totalRecovered.toString()
                tvDeathTotal.text = countryCase.totalDeaths.toString()
            }
        }
    }
}