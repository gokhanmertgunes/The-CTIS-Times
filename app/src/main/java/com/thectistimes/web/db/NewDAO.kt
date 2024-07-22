package com.thectistimes.web.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.thectistimes.web.model.New
import com.thectistimes.web.util.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface NewDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNew(newsItem: New)

    @Update
    fun updateNew(newsItem: New)

    @Delete
    fun deleteNew(newsItem: New)

    @Query("DELETE FROM ${Constants.TABLENAME}")
    fun deleteAllNews()

    @Query("SELECT * FROM ${Constants.TABLENAME} ORDER BY id DESC")
    fun getAllNews(): LiveData<List<New>>

    @Query("SELECT * FROM ${Constants.TABLENAME} WHERE id =:id")
    fun getNewById(id:Int): Flow<New>

    @Query("SELECT * FROM ${Constants.TABLENAME} WHERE title LIKE :title OR content LIKE :title OR author LIKE :title")
    fun getNewsByTitle(title:String): Flow<List<New>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllNews(newsItems: ArrayList<New>){
        newsItems.forEach{
            insertNew(it)
        }
    }
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(newsItems: List<New>)
}