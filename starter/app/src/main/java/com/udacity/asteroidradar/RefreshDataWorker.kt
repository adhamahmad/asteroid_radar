package com.udacity.asteroidradar

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context,params: WorkerParameters) :CoroutineWorker(appContext,params){
    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }
    override suspend fun doWork(): Result {
        val database = AsteroidDatabase.getInstance(applicationContext)
        val repo = MainRepo(database)
        return try {
            repo.getAsteroids()
            Result.success()
        }catch (e:HttpException){
            Result.retry()
        }
    }
}