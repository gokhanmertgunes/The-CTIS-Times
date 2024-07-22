package com.thectistimes.web.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.thectistimes.web.model.New
import com.thectistimes.web.model.Saved
import com.thectistimes.web.util.Constants

@Database(entities = [New::class , Saved::class], version = 9)
abstract class NewRoomDatabase : RoomDatabase() {
    abstract fun newDao(): NewDAO
    abstract fun savedDao(): SavedDAO

    companion object{
        @Volatile
        private var INSTANCE: NewRoomDatabase?=null

        fun getDatabase(context:Context): NewRoomDatabase {
            val tempInstance = INSTANCE
            if(tempInstance !=null){
                return  tempInstance
            }
            synchronized(this){
                val  instance =Room.databaseBuilder(context.applicationContext, NewRoomDatabase::class.java, Constants.DATABASENAME).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}