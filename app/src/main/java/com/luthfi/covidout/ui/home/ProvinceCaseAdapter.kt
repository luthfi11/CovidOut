package com.luthfi.covidout.ui.home

import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luthfi.covidout.R
import com.luthfi.covidout.data.model.ProvinceResponse
import com.luthfi.covidout.ui.webview.WebViewActivity
import com.luthfi.covidout.utils.formatNumber
import com.luthfi.covidout.utils.provinceData
import com.luthfi.covidout.utils.roundNumber
import kotlinx.android.synthetic.main.item_case_by_province.view.*
import kotlinx.android.synthetic.main.layout_province.view.*

class ProvinceCaseAdapter(private val responseList: List<ProvinceResponse>) :
    RecyclerView.Adapter<ProvinceCaseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_case_by_province, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return responseList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(responseList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bindView(provinceResponse: ProvinceResponse) {
            with(itemView) {
                tvCaseTotal.text =
                    "Total Kasus : " + provinceResponse.attributes.positiveCase.toString()
                tvRecoverTotal.text =
                    "Sembuh : " + provinceResponse.attributes.recoverCase.toString()
                tvDeathTotal.text =
                    "Meninggal : " + provinceResponse.attributes.deathCase.toString()
                tvProvince.text = provinceResponse.attributes.province

                setOnClickListener {
                    showProvinceDialog(it.context, provinceResponse)
                }
            }
        }

        @SuppressLint("InflateParams", "SetTextI18n")
        private fun showProvinceDialog(context: Context, province: ProvinceResponse) {
            val provinceDialog =
                LayoutInflater.from(context).inflate(R.layout.layout_province, null)

            val dialog = Dialog(context).apply {
                setContentView(provinceDialog)
                window?.setBackgroundDrawableResource(android.R.color.white)
                window?.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.MATCH_PARENT
                );
            }

            dialog.show()

            val provinceData = provinceData.filter { it.name == province.attributes.province }
            val activeCase =
                province.attributes.positiveCase - province.attributes.recoverCase - province.attributes.deathCase

            val recoverRate =
                roundNumber((province.attributes.recoverCase.toDouble() / province.attributes.positiveCase) * 100)
            val deathRate =
                roundNumber((province.attributes.deathCase.toDouble() / province.attributes.positiveCase) * 100)
            val activeRate =
                roundNumber((activeCase.toDouble() / province.attributes.positiveCase) * 100)

            with(provinceDialog) {
                Glide.with(context).load(provinceData[0].logo).into(imgProvince)

                if (provinceData[0].webUrl == "") {
                    tvWebUrl.text = resources.getText(R.string.not_available)
                    tvWebUrl.setTextColor(Color.parseColor("#000000"))
                } else {
                    tvWebUrl.text = provinceData[0].webUrl
                    tvWebUrl.setOnClickListener {
                        val intent = Intent(context, WebViewActivity::class.java).apply {
                            putExtra("url", provinceData[0].webUrl)
                            putExtra("title", province.attributes.province)
                        }
                        context.startActivity(intent)
                    }
                }

                tvProvinceName.text = province.attributes.province
                tvProvinceCaseTotal.text = formatNumber(province.attributes.positiveCase)
                tvProvinceRecoverTotal.text = "${province.attributes.recoverCase} ($recoverRate%)"
                tvProvinceDeathTotal.text = "${province.attributes.deathCase} ($deathRate%)"
                tvProvinceActiveCase.text = "${formatNumber(activeCase)} ($activeRate%)"

                tvClose.setOnClickListener { dialog.dismiss() }

            }
        }
    }
}