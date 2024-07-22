package com.thectistimes.web
import android.content.Context
import android.os.Bundle
import android.telecom.Call
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thectistimes.web.adapter.MixedNewsAdapter
import com.thectistimes.web.adapter.SearchNewsAdapter
import com.thectistimes.web.api.ApiClient
import com.thectistimes.web.api.ApiService
import com.thectistimes.web.db.NewViewModel
import com.thectistimes.web.model.New
import retrofit2.Callback
import retrofit2.Response

class SearchFragment(contextMain: Context) : Fragment() {
    private lateinit var searchEditText: EditText
    private lateinit var searchRecyclerView: RecyclerView
    private lateinit var searchAdapter: SearchNewsAdapter
    private lateinit var newViewModel: NewViewModel
    private var context:Context = contextMain

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        searchEditText = view.findViewById(R.id.searchEditText)
        searchRecyclerView = view.findViewById(R.id.searchRecyclerView)
        newViewModel = ViewModelProvider(this).get(NewViewModel::class.java)
        setupRecyclerView()
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Optional implementation
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                searchDatabase(s.toString())
            }

            override fun afterTextChanged(s: Editable) {
                // Optional implementation
            }
        })
        return view
    }

    private fun setupRecyclerView() {
        searchRecyclerView.layoutManager = LinearLayoutManager(context)
        searchAdapter = SearchNewsAdapter(context, listOf())
        searchRecyclerView.adapter = searchAdapter
    }

    private fun searchDatabase(key: String) {
        val searchKey = "%$key%"
        newViewModel.searchNew(searchKey).observe(viewLifecycleOwner) { searchResultList ->
            searchAdapter.updateNewsList(searchResultList)
        }
    }
}