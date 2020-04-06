package com.luthfi.covidout.ui.learn

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luthfi.covidout.R
import com.luthfi.covidout.data.model.Article
import com.luthfi.covidout.ui.article.ArticleActivity
import kotlinx.android.synthetic.main.item_learn.view.*

class ArticleAdapter(private val article: List<Article>) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_learn, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return article.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(article[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindItem(article: Article) {
            with(itemView) {
                Glide.with(context).load(article.banner).into(imgBanner)
                tvTitle.text = article.title
                btnLearn.setOnClickListener {
                    val intent = Intent(context, ArticleActivity::class.java).apply {
                        putExtra("data", article)
                    }
                    context.startActivity(intent)
                }
            }

        }
    }
}