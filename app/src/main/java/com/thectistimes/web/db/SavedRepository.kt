package com.thectistimes.web.db

import androidx.lifecycle.LiveData
import com.thectistimes.web.model.Saved
import kotlinx.coroutines.flow.Flow

class SavedRepository(private val savedDAO: SavedDAO) {
    val readAlldata: LiveData<List<Saved>> = savedDAO.getAllSaveds()

    fun insertSaved(saved: Saved){
        savedDAO.insertSaved(saved)
    }
    fun insertSaveds(customers:ArrayList<Saved>){
        savedDAO.insertAllSaveds(customers)
    }

    fun updateSaved(saved: Saved){
        savedDAO.updateSaved(saved)
    }

    fun deleteSaved(saved: Saved){
        savedDAO.deleteSaved(saved)
    }

    fun deleteAllSaveds(){
        savedDAO.deleteAllSaveds()
    }

    fun getAllSaveds(): LiveData<List<Saved>> {
        return savedDAO.getAllSaveds()
    }

    fun getSavedById(id:Int): Saved {
        return savedDAO.getSavedById(id)
    }
}