package com.thectistimes.web

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.thectistimes.web.model.New
import com.thectistimes.web.util.Constants

class NewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val newsTitle: TextView = findViewById(R.id.newsTitle)
        val newsImage: ImageView = findViewById(R.id.newsImage)
        val newsContent: TextView = findViewById(R.id.newsContent)
        val newsAuthorDate: TextView = findViewById(R.id.newsAuthorDate)

        val newsItem = intent.getParcelableExtra<New>("newsItem")

        newsTitle.text = newsItem?.title
        newsContent.text = newsItem?.content
        newsAuthorDate.text = "${newsItem?.author} - ${newsItem?.date}"

        Glide.with(this).load(newsItem?.imageUrl).into(newsImage)

        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
    }
}
