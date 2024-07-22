package com.thectistimes.web.db

import android.util.Log
import androidx.lifecycle.LiveData
import com.thectistimes.web.model.New
import kotlinx.coroutines.flow.Flow

class NewRepository(private val newDAO: NewDAO) {
    val readAlldata: LiveData<List<New>> = newDAO.getAllNews()

    fun insertNew(new: New){
        newDAO.insertNew(new)
    }
    fun insertNews(news:List<New>){
        newDAO.insertAll(news)
        Log.d("asd", news.toString())
    }

    fun updateNew(new: New){
        newDAO.updateNew(new)
    }

    fun deleteNew(new: New){
        newDAO.deleteNew(new)
    }

    fun deleteAllNews(){
        newDAO.deleteAllNews()
    }

    fun getAllNews(): LiveData<List<New>> {
        return newDAO.getAllNews()
    }

    fun getNewById(id:Int): Flow<New> {
        return newDAO.getNewById(id)
    }
    fun getNewsByTitle(title:String): Flow<List<New>> {
        return newDAO.getNewsByTitle(title)
    }
}