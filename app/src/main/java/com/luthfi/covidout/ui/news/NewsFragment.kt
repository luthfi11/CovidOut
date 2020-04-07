package com.luthfi.covidout.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.luthfi.covidout.R
import com.luthfi.covidout.data.model.News
import com.luthfi.covidout.utils.gone
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter
    private val newsList = mutableListOf<News>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()

        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        viewModel.allNews?.observe(viewLifecycleOwner, newsObserver)
    }

    private val newsObserver = Observer<List<News>> {
        adapter.setNewsData(it)
        progressBar.gone()
    }

    private fun setUpRecycler() {
        adapter = NewsAdapter(newsList)
        rvNews.layoutManager = LinearLayoutManager(context)
        rvNews.adapter = adapter
    }

}