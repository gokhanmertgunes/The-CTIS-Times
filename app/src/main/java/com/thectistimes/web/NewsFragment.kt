package com.thectistimes.web

import android.content.Context
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thectistimes.web.adapter.MixedNewsAdapter
import com.thectistimes.web.api.ApiClient
import com.thectistimes.web.api.ApiService
import com.thectistimes.web.db.NewViewModel
import com.thectistimes.web.model.New
import retrofit2.Callback
import retrofit2.Response

class NewsFragment(contextMain: Context) : Fragment() {
    private lateinit var mixedRecyclerView: RecyclerView
    private lateinit var mixedNewsAdapter: MixedNewsAdapter
    private lateinit var newViewModel: NewViewModel
    private var context:Context = contextMain


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        mixedRecyclerView = view.findViewById(R.id.mixedRecyclerView)
        newViewModel = ViewModelProvider(this).get(NewViewModel::class.java)
        setupRecyclerView()
        fetchNewsFromApi()
        return view
    }

    private fun setupRecyclerView() {
        mixedRecyclerView.layoutManager = LinearLayoutManager(context)
        mixedNewsAdapter = MixedNewsAdapter(listOf(), listOf(), context)
        mixedRecyclerView.adapter = mixedNewsAdapter
    }

    private fun fetchNewsFromApi() {
        val apiService = ApiClient.getClient().create(ApiService::class.java)
        apiService.getNews().enqueue(object : Callback<List<New>> {
            override fun onResponse(call: retrofit2.Call<List<New>>, response: Response<List<New>>) {
                if (response.isSuccessful) {
                    val newsList = response.body() ?: emptyList()
                    newViewModel.addNews(newsList)
                    val (horizontalNews, verticalNews) = newsList.partition { it.id < 7 } // Assuming 'id' is available
                    mixedNewsAdapter.updateData(horizontalNews, verticalNews)
                } else {
                    Log.e("NewsFragment", "API Response not successful")
                }
            }

            override fun onFailure(call: retrofit2.Call<List<New>>, t: Throwable) {
                Log.e("NewsFragment", "API call failed", t)
            }
        })
    }
}
