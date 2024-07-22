package com.thectistimes.web.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.thectistimes.web.model.New
import com.thectistimes.web.model.Saved
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SavedViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<Saved>>
    private val repository:SavedRepository
    init {
        val savedDao= NewRoomDatabase.getDatabase(application).savedDao()
        repository = SavedRepository(savedDao)
        readAllData = repository.readAlldata
    }
    fun getAllSaveds(): LiveData<List<Saved>> {
        return repository.getAllSaveds()
    }

    fun addSaved(saved: Saved){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertSaved(saved)
        }
    }
    fun addSaveds(saveds: List<Saved>){
        viewModelScope.launch(Dispatchers.IO) {
            saveds.forEach{
                repository.insertSaved(it)
            }
        }
    }
    fun deleteSaved(saved: Saved){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteSaved(saved)
        }
    }
    fun deleteAllSaveds(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllSaveds()
        }
    }
    fun updateSaved(saved: Saved){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateSaved(saved)
        }
    }

    fun convertNewToSaved(newItem: New):Saved{
        var s:Saved = Saved(newItem.id)
        return s
    }
}