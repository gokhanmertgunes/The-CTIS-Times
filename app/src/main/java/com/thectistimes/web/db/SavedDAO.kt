package com.thectistimes.web.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.thectistimes.web.model.Saved
import com.thectistimes.web.util.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSaved(savedItem: Saved)

    @Update
    fun updateSaved(savedItem: Saved)

    @Delete
    fun deleteSaved(savedItem: Saved)

    @Query("DELETE FROM ${Constants.TABLENAMESAVED}")
    fun deleteAllSaveds()

    @Query("SELECT * FROM ${Constants.TABLENAMESAVED} ORDER BY id DESC")
    fun getAllSaveds(): LiveData<List<Saved>>

    @Query("SELECT * FROM ${Constants.TABLENAMESAVED} WHERE id =:id")
    fun getSavedById(id:Int): Saved

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSaveds(savedItems: ArrayList<Saved>){
        savedItems.forEach{
            insertSaved(it)
        }
    }
}