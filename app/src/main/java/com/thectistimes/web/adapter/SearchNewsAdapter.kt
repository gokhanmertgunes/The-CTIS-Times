package com.thectistimes.web.adapter

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.util.Log
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.bumptech.glide.Glide
import com.thectistimes.web.MainActivity
import com.thectistimes.web.NewsActivity
import com.thectistimes.web.R
import com.thectistimes.web.backgroundservice.CustomWorker
import com.thectistimes.web.db.SavedViewModel
import com.thectistimes.web.model.New
import com.thectistimes.web.model.Saved
import com.thectistimes.web.util.Constants

class SearchNewsAdapter(
    private val context: Context,
    private var newsList: List<New>, // New model listesi
) : RecyclerView.Adapter<SearchNewsAdapter.SearchedNewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedNewsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_searched_news, parent, false)
        return SearchedNewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchedNewsViewHolder, position: Int) {
        val newsItem = newsList[position]
        holder.bind(newsItem, context)
    }

    override fun getItemCount() = newsList.size

    fun updateNewsList(newNewsList: List<New>) {
        newsList = newNewsList
        notifyDataSetChanged()
    }

    class SearchedNewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.imageViewVerticalNews)
        private val authorView: TextView = view.findViewById(R.id.textViewVerticalAuthor)
        private val titleView = view.findViewById<TextView>(R.id.textViewVerticalTitle)
        private val playSoundButton: ImageButton = view.findViewById(R.id.buttonPlaySound)
        private val saveButton: ImageButton = view.findViewById(R.id.buttonSave)
        private val layout:LinearLayout = view.findViewById(R.id.layout)
        private lateinit var gestureDetector: GestureDetector
        private var newsItem: New? = null
        private var context: Context? = null
        private lateinit var viewModel: SavedViewModel
        fun bind(newsItem: New, context: Context) {
            viewModel = ViewModelProvider(context as MainActivity).get(SavedViewModel::class.java)
            this.newsItem = newsItem
            this.context = context

            val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                    val intent = Intent(context, NewsActivity::class.java)
                    intent.putExtra("newsItem", newsItem)
                    context.startActivity(intent)
                    return true
                }

                override fun onDoubleTap(e: MotionEvent): Boolean {
                    saveNewsItem()
                    return true
                }
            }
            gestureDetector = GestureDetector(context, gestureListener)

            layout.setOnTouchListener { _, event ->
                gestureDetector.onTouchEvent(event)
                true
            }

            Glide.with(itemView.context)
                .load(newsItem.imageUrl)
                .into(imageView)

            authorView.text = newsItem.author
            titleView.text = newsItem.title

            playSoundButton.setOnClickListener {
                val mediaPlayer = MediaPlayer.create(context, R.raw.habertikla)
                mediaPlayer.start()

                mediaPlayer.setOnCompletionListener {
                    it.release()
                }
            }

            saveButton.setOnClickListener {
                val savedId = viewModel.convertNewToSaved(newsItem).id

                val saveWorkRequest = OneTimeWorkRequest.Builder(CustomWorker::class.java)
                    .setInputData(Data.Builder().putString("id", savedId.toString()).build())
                    .build()

                WorkManager.getInstance(context).enqueue(saveWorkRequest)
                Toast.makeText(context, "Successfully Saved.", Toast.LENGTH_SHORT).show()
            }

            layout.setOnClickListener {
                val intent = Intent(context, NewsActivity::class.java)
                intent.putExtra("newsItem", newsItem)
                context.startActivity(intent)
            }
        }
        private fun saveNewsItem() {
            newsItem?.let {
                val s: Saved = viewModel.convertNewToSaved(it)
                viewModel.addSaved(s)
                Toast.makeText(context, "Successfully Saved.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
