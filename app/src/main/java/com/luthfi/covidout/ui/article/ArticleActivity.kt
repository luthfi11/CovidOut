package com.luthfi.covidout.ui.article

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.luthfi.covidout.R
import com.luthfi.covidout.data.model.Article
import kotlinx.android.synthetic.main.activity_article.*
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter

class ArticleActivity : AppCompatActivity() {

    private lateinit var imageAdapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val data = intent.getParcelableExtra<Article>("data")
        data?.let {
            showArticle(it)
            imageAdapter = ImageAdapter(this, it.image!!)
            viewPager.adapter = imageAdapter
            circle.setViewPager(viewPager)
        }
    }

    private fun showArticle(article: Article) {
        with(article) {
            tvTitle.text = title
            tvContent.setHtml(content, HtmlHttpImageGetter(tvContent))
        }
    }
}
