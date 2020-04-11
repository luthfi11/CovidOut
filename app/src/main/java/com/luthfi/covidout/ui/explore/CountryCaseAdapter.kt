package com.luthfi.covidout.ui.explore

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.luthfi.covidout.R
import com.luthfi.covidout.data.model.CountryCase
import com.luthfi.covidout.utils.formatNumber
import kotlinx.android.synthetic.main.item_country.view.*

class CountryCaseAdapter(private var countryCaseList: List<CountryCase>) :
    RecyclerView.Adapter<CountryCaseAdapter.ViewHolder>() {

    private var onCountryCallback: OnCountryCallback? = null

    fun setOnCountryClick(onCountryCallback: OnCountryCallback) {
        this.onCountryCallback = onCountryCallback
    }

    fun setCaseData(data: List<CountryCase>) {
        countryCaseList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_country, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return countryCaseList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(countryCaseList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bindItem(countryCase: CountryCase) {
            with(itemView) {
                tvCountry.text = countryCase.country
                tvCaseTotal.text =
                    "${formatNumber(countryCase.totalConfirmed)} (+${formatNumber(countryCase.newConfirmed)})"
                tvRecoverTotal.text =
                    "${formatNumber(countryCase.totalRecovered)} (+${formatNumber(countryCase.newRecovered)})"
                tvDeathTotal.text =
                    "${formatNumber(countryCase.totalDeaths)} (+${formatNumber(countryCase.newDeaths)})"

                setOnClickListener {
                    onCountryCallback?.onCountrySelected(countryCase)
                }
            }
        }
    }

    interface OnCountryCallback {
        fun onCountrySelected(countryCase: CountryCase)
    }
}