package com.luthfi.covidout.ui.learn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.luthfi.covidout.R
import com.luthfi.covidout.ui.webview.WebViewActivity
import com.luthfi.covidout.utils.articleData
import kotlinx.android.synthetic.main.activity_learn.*

class LearnActivity : AppCompatActivity() {

    private lateinit var adapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn)
        setUpToolbar()
        setUpRecycler()
        onButtonAction()
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUpRecycler() {
        adapter = ArticleAdapter(articleData)
        rvArticle.layoutManager = LinearLayoutManager(this)
        rvArticle.adapter = adapter
    }

    private fun onButtonAction() {
        btnCheck.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java).apply {
                putExtra("url", "https://corona.alodokter.com/cek-risiko-tertular-virus-corona-gratis")
                putExtra("title", "Periksa Mandiri by Alodokter")
            }
            startActivity(intent)
        }
    }
}
