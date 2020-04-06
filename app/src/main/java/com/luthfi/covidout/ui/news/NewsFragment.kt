package com.luthfi.covidout.ui.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager

import com.luthfi.covidout.R
import com.luthfi.covidout.data.model.News
import kotlinx.android.synthetic.main.fragment_news.*

/**
 * A simple [Fragment] subclass.
 */
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
        newsList.clear()
        newsList.addAll(it)
        adapter.notifyDataSetChanged()
    }

    private fun setUpRecycler() {
        adapter = NewsAdapter(newsList)
        rvNews.layoutManager = LinearLayoutManager(context)
        rvNews.adapter = adapter
    }

}