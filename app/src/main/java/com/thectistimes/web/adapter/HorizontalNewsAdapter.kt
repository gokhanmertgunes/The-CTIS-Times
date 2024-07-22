package com.thectistimes.web.adapter

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
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

class HorizontalNewsAdapter(var newsList: List<New>, private val context: Context) : RecyclerView.Adapter<HorizontalNewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageViewNews)
        val titleView: TextView = view.findViewById(R.id.textViewTitle)
        val layout: LinearLayout = view.findViewById(R.id.layout)
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
        }
        private fun saveNewsItem() {
            newsItem?.let {
                val s: Saved = viewModel.convertNewToSaved(it)
                viewModel.addSaved(s)
                Toast.makeText(context, "Successfully Saved.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_horizontal_news, parent, false)
        return NewsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItem = newsList[position]
        var imgUrlAddress = newsItem.imageUrl

        Glide.with(context)
            .load(imgUrlAddress)
            .into(holder.imageView)
        holder.titleView.text = newsItem.title

        holder.bind(newsItem,context)
    }

    override fun getItemCount() = newsList.size

    fun updateData(newNewsList: List<New>) {
        this.newsList = newNewsList
        notifyDataSetChanged()
    }
}
