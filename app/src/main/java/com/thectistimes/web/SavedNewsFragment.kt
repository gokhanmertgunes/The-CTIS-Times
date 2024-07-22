package com.thectistimes.web

import android.content.Context
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thectistimes.web.adapter.MixedNewsAdapter
import com.thectistimes.web.adapter.SavedNewsAdapter
import com.thectistimes.web.api.ApiClient
import com.thectistimes.web.api.ApiService
import com.thectistimes.web.db.NewViewModel
import com.thectistimes.web.db.SavedViewModel
import com.thectistimes.web.model.New
import com.thectistimes.web.model.Saved
import retrofit2.Callback
import retrofit2.Response

class SavedNewsFragment(contextMain: Context) : Fragment() {
    private lateinit var mixedRecyclerView: RecyclerView
    private lateinit var savedNewsAdapter: SavedNewsAdapter
    private lateinit var savedViewModel: SavedViewModel
    private lateinit var newViewModel: NewViewModel
    private var context:Context = contextMain

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_saved_news, container, false)
        mixedRecyclerView = view.findViewById(R.id.mixedRecyclerView)
        savedViewModel = ViewModelProvider(this).get(SavedViewModel::class.java)
        newViewModel = ViewModelProvider(this).get(NewViewModel::class.java)
        setupRecyclerView(context)
        fetchSavedNews()
        return view
    }

    private fun setupRecyclerView(context: Context) {
        mixedRecyclerView.layoutManager = LinearLayoutManager(context)
        savedNewsAdapter = SavedNewsAdapter(context, listOf()) { news ->
            savedViewModel.deleteSaved(Saved(news.id))
            savedNewsAdapter.notifyDataSetChanged()
        }
        mixedRecyclerView.adapter = savedNewsAdapter
    }

    private fun fetchSavedNews() {
        savedViewModel.readAllData.observe(viewLifecycleOwner) { savedNewsIds ->
            val savedNewsDetails = mutableListOf<New>()

            savedNewsIds.forEach { saved ->
                newViewModel.getNew(saved.id).observe(viewLifecycleOwner) { newsDetail ->
                    newsDetail?.let {
                        savedNewsDetails.add(it)
                        if (savedNewsDetails.size == savedNewsIds.size) {
                            savedNewsAdapter.updateNewsList(savedNewsDetails)
                        }
                    }
                }
            }
        }
    }
}
