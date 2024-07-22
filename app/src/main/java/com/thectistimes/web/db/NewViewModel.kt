package com.thectistimes.web.db

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.thectistimes.web.model.New
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<New>>
    private val repository:NewRepository
    init {
        val newDao= NewRoomDatabase.getDatabase(application).newDao()
        repository = NewRepository(newDao)
        readAllData = repository.readAlldata
    }
    fun getAllNews(): LiveData<List<New>> {
        return repository.getAllNews()
    }

    fun addNew(new: New){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertNew(new)
        }
    }
    fun addNews(news: List<New>){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNews(news)
        }
    }
    fun deleteNew(new: New){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteNew(new)
        }
    }
    fun deleteAllNews(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllNews()
        }
    }
    fun updateNew(new: New){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateNew(new)
        }
    }
    fun searchNew(title:String): LiveData<List<New>> {
        return repository.getNewsByTitle(title).asLiveData()
    }

    fun getNew(id:Int):LiveData<New> {
        return repository.getNewById(id).asLiveData()
    }
}