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
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
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

class MixedNewsAdapter(
    private var horizontalNewsList: List<New>,
    private var verticalNewsList: List<New>,
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HORIZONTAL = 0
        private const val TYPE_VERTICAL = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HORIZONTAL else TYPE_VERTICAL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HORIZONTAL -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.horizontal_recycler_container, parent, false)
                HorizontalViewHolder(view)
            }
            TYPE_VERTICAL -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vertical_news, parent, false)
                VerticalViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HorizontalViewHolder -> {
                holder.bind(horizontalNewsList)
            }
            is VerticalViewHolder -> {
                val newsItem = verticalNewsList[position - 1] // Çünkü ilk öğe yataydır
                holder.bind(newsItem, context)
            }
        }
    }

    override fun getItemCount(): Int {
        return 1 + verticalNewsList.size // 1 yatay + dikey öğelerin sayısı
    }

    fun updateData(horizontalNews: List<New>, verticalNews: List<New>) {
        this.horizontalNewsList = horizontalNews
        this.verticalNewsList = verticalNews
        notifyDataSetChanged()
    }

    class HorizontalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val recyclerView: RecyclerView = view.findViewById(R.id.horizontalRecyclerView)

        fun bind(newsList: List<New>) {
            if (recyclerView.adapter == null) {
                recyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                recyclerView.adapter = HorizontalNewsAdapter(newsList, itemView.context)
            } else {
                (recyclerView.adapter as HorizontalNewsAdapter).updateData(newsList)
            }
        }
    }

    class VerticalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.imageViewVerticalNews)
        private val authorView: TextView = view.findViewById(R.id.textViewVerticalAuthor)
        private val titleView = view.findViewById<TextView>(R.id.textViewVerticalTitle)
        private val playSoundButton: ImageButton = view.findViewById(R.id.buttonPlaySound)
        private val saveButton: ImageButton = view.findViewById(R.id.buttonSave)
        private val layout: LinearLayout = view.findViewById(R.id.layout)
        private lateinit var viewModel: SavedViewModel
        private var newsItem: New? = null
        private var context: Context? = null
        private lateinit var gestureDetector: GestureDetector
        fun bind(newsItem: New, context: Context) {
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

            viewModel = ViewModelProvider(context as MainActivity).get(SavedViewModel::class.java)

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
