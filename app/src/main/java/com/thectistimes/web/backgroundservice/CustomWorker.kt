package com.thectistimes.web.backgroundservice

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.thectistimes.web.util.Utils
import androidx.work.Data
import androidx.work.Worker
import com.thectistimes.web.db.NewRoomDatabase
import com.thectistimes.web.db.SavedRepository
import com.thectistimes.web.model.Saved

class CustomWorker(var context: Context, var workerParams: WorkerParameters, ):
    Worker(context, workerParams){
    override fun doWork(): ListenableWorker.Result {

        val id: Int = getInputData().getString("id").toString().toInt()

        val savedRoomDatabase = NewRoomDatabase.getDatabase(context)
        val savedDao = savedRoomDatabase.savedDao()

        val repository = SavedRepository(savedDao)

        return try {
            Log.d(Utils.TAGFORLAGCAT, "doWork Called, inputs: $id")

            repository.insertSaved(Saved(id))

            val outputData = Data.Builder().putString("result", "Saved inserted").build()
            Log.d(Utils.TAGFORLAGCAT, "End of worker")
            ListenableWorker.Result.success(outputData)
        } catch (throwable: Throwable) {
            Log.d(Utils.TAGFORLAGCAT, "Error Sending Notification" + throwable.message)
            ListenableWorker.Result.failure()
        }
    }
}