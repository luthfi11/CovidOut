package com.luthfi.covidout.ui.maps

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.luthfi.covidout.R
import com.luthfi.covidout.utils.MyBrowser
import kotlinx.android.synthetic.main.fragment_maps.*

class MapsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBrowser()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpBrowser() {
        webMap.settings.javaScriptEnabled = true
        webMap.webViewClient = MyBrowser(progressBar)
        webMap.loadUrl("https://www.google.co.id/covid19-map/")
    }
}
