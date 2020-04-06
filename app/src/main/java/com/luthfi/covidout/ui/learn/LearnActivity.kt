package com.luthfi.covidout.ui.learn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.luthfi.covidout.R
import com.luthfi.covidout.utils.articleData
import kotlinx.android.synthetic.main.activity_learn.*

class LearnActivity : AppCompatActivity() {

    private lateinit var adapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn)
        setUpToolbar()
        setUpRecycler()
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
}
