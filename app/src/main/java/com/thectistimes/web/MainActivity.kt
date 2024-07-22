package com.thectistimes.web

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.thectistimes.web.adapter.MixedNewsAdapter
import com.thectistimes.web.adapter.SavedNewsAdapter
import com.thectistimes.web.adapter.SearchNewsAdapter
import com.thectistimes.web.api.ApiClient
import com.thectistimes.web.api.ApiService
import com.thectistimes.web.db.NewViewModel
import com.thectistimes.web.db.SavedViewModel
import com.thectistimes.web.model.New
import com.thectistimes.web.model.Saved
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var newsFragment: NewsFragment
    private lateinit var savedNewsFragment: SavedNewsFragment
    private lateinit var searchFragment: SearchFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newsFragment = NewsFragment(this)
        savedNewsFragment = SavedNewsFragment(this)
        searchFragment = SearchFragment(this)

        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, newsFragment).commit()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, newsFragment).commit()
                    true
                }
                R.id.navigation_saved -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, savedNewsFragment).commit()
                    true
                }
                R.id.navigation_search -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, searchFragment).commit()
                    true
                }
                else -> false
            }
        }
    }
}
