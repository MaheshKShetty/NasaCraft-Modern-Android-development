package com.example.nasaapp

import android.content.Context
import androidx.work.*
import com.example.nasaapp.db.AstronomyDao
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DBDeleteWorkManager @Inject constructor( val astronomyDao: AstronomyDao) {

    fun createTaskToDeleteDb(context: Context) {

        // task will get updated for every 6 hours this can be modified based on the conditions
        val workRequest = PeriodicWorkRequest.Builder(
            DeleteDBWorkManager::class.java,
            1,
            TimeUnit.HOURS,
            PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS,
            TimeUnit.MILLISECONDS
        )
            .setInitialDelay(6, TimeUnit.HOURS)
            .addTag("send_reminder_periodic")
            .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                "send_reminder_periodic",
                ExistingPeriodicWorkPolicy.REPLACE,
                workRequest
            )
    }

}

class DeleteDBWorkManager(var context: Context, workerParams: WorkerParameters,val astronomyDao: AstronomyDao) : CoroutineWorker(
    context,
    workerParams) {

    override suspend fun doWork():  Result = coroutineScope {
        val response = astronomyDao.getAstronomy()
        response.collect {
            astronomyDao.deleteAll(response = it)
        }
        Result.success()
    }
}
