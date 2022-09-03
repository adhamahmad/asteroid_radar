package com.udacity.asteroidradar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException

class MainRepo (private val database: AsteroidDatabase){

    val asteroids:LiveData<List<Asteroid>> =Transformations.map (
        database.databaseAsteroidDao.getAsteroids(Constants.today)
        ){
        it.asDomainModel()
    }

        suspend fun getAsteroids(){
        withContext(Dispatchers.IO){
            try {
                val response = NasaApi.retrofitService.getAsteriods()
                val result = parseAsteroidsJsonResult(JSONObject(response)) // pass response as JSON object
                database.databaseAsteroidDao.insert(*result.asDatabaseModel())

            }catch (e: HttpException){
                Log.e("getAsteroids", e.printStackTrace().toString())
            }
        }
    }
}