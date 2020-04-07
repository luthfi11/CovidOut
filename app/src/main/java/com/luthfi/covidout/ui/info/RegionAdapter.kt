package com.luthfi.covidout.ui.info

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luthfi.covidout.R
import com.luthfi.covidout.data.model.Region
import com.luthfi.covidout.ui.webview.WebViewActivity
import kotlinx.android.synthetic.main.item_region.view.*

class RegionAdapter(private var regionList: List<Region>): RecyclerView.Adapter<RegionAdapter.ViewHolder>() {

    fun setRegionData(data: List<Region>) {
        regionList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_region, parent, false))
    }

    override fun getItemCount(): Int {
        return regionList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(regionList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindItem(region: Region) {
            with(itemView) {
                Glide.with(context).load(region.logo).placeholder(R.color.colorMuted).into(imgProvince)
                tvProvince.text = region.name
                tvProvinceUrl.text = region.webUrl

                setOnClickListener {
                    val intent = Intent(context, WebViewActivity::class.java).apply {
                        putExtra("title", region.name)
                        putExtra("url", region.webUrl)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }
}