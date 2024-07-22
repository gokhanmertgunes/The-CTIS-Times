package com.thectistimes.web.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thectistimes.web.NewsActivity
import com.thectistimes.web.R
import com.thectistimes.web.model.New
import com.thectistimes.web.util.Constants

class SavedNewsAdapter(
    private val context: Context,
    private var newsList: List<New>, // New model listesi
    private val onDeleteClicked: (New) -> Unit // Silme işlemi için callback
) : RecyclerView.Adapter<SavedNewsAdapter.SavedNewsViewHolder>() {

    class SavedNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewVerticalNews)
        val authorView: TextView = itemView.findViewById(R.id.textViewVerticalAuthor)
        val titleView: TextView = itemView.findViewById(R.id.textViewVerticalTitle)
        val deleteButton: ImageButton = itemView.findViewById(R.id.buttonDelete)
        val layout: LinearLayout = itemView.findViewById(R.id.layout)
        fun bind(newsItem: New,context: Context) {
            Glide.with(itemView.context).load(newsItem.imageUrl).into(imageView)
            authorView.text = newsItem.author
            titleView.text = newsItem.title

            layout.setOnClickListener{
                val intent = Intent(context, NewsActivity::class.java)
                intent.putExtra("newsItem", newsItem)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedNewsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_saved_news, parent, false)
        return SavedNewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavedNewsViewHolder, position: Int) {
        val newsItem = newsList[position]
        holder.bind(newsItem,context)

        holder.deleteButton.setOnClickListener {
            onDeleteClicked(newsItem)
            newsList = newsList.filter { it.id != newsItem.id }
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount() = newsList.size

    fun updateNewsList(newNewsList: List<New>) {
        newsList = newNewsList
        notifyDataSetChanged()
    }
}
