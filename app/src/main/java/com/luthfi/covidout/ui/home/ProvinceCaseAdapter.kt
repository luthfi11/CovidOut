package com.luthfi.covidout.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.luthfi.covidout.R
import com.luthfi.covidout.data.model.ProvinceResponse
import kotlinx.android.synthetic.main.item_case_by_province.view.*

class ProvinceCaseAdapter(private val responseList: List<ProvinceResponse>): RecyclerView.Adapter<ProvinceCaseAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_case_by_province, parent, false))
    }

    override fun getItemCount(): Int {
        return responseList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(responseList[position])
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        fun bindView(provinceResponse: ProvinceResponse) {
            with(itemView) {
                tvCaseTotal.text = "Total Kasus : "+provinceResponse.attributes.positiveCase.toString()
                tvRecoverTotal.text = "Sembung : "+provinceResponse.attributes.recoverCase.toString()
                tvDeathTotal.text = "Meninggal : "+provinceResponse.attributes.deathCase.toString()
                tvProvince.text = provinceResponse.attributes.province
            }
        }
    }
}