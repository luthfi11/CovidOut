package com.luthfi.covidout.ui.article

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.luthfi.covidout.R
import com.luthfi.covidout.data.model.Article
import kotlinx.android.synthetic.main.activity_article.*
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter

class ArticleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val data = intent.getParcelableExtra<Article>("data")
        data?.let {
            showArticle(it)
        }
    }

    private fun showArticle(article: Article) {
        with(article) {
            Glide.with(applicationContext).load(banner).into(imgBanner)
            tvTitle.text = title
            tvContent.setHtml(content, HtmlHttpImageGetter(tvContent))
        }
    }
}
