package com.luthfi.covidout.ui.news

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luthfi.covidout.R
import com.luthfi.covidout.data.model.News
import com.luthfi.covidout.ui.webview.WebViewActivity
import com.luthfi.covidout.utils.formatUTCDate
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter(private var newsList: List<News>): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    fun setNewsData(data: List<News>) {
        newsList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false))
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(newsList[position])
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bindItem(news: News) {
            with(itemView) {
                Glide.with(context).load(news.urlToImage).placeholder(R.color.colorMuted).into(imgNews)
                tvTitle.text = news.title
                tvDescription.text = news.description
                tvSource.text = news.source?.name

                news.publishedAt?.let {
                    tvDate.text = formatUTCDate(it, "dd MMMM")
                }

                setOnClickListener {
                    val intent = Intent(context, WebViewActivity::class.java).apply {
                        putExtra("title", "Berita")
                        putExtra("url", news.url)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }
}