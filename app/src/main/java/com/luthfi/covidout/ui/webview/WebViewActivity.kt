package com.luthfi.covidout.ui.webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import com.luthfi.covidout.R
import com.luthfi.covidout.utils.MyBrowser
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val title = intent.getStringExtra("title")
        val url = intent.getStringExtra("url")

        setUpToolbar(title)
        setUpBrowser(url)
    }

    private fun setUpToolbar(title: String?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = title
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpBrowser(url: String?) {
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = MyBrowser(progressBar)
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        webView.loadUrl(url)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}