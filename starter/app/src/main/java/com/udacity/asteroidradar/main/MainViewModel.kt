package com.udacity.asteroidradar.main

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.udacity.asteroidradar.*
import com.udacity.asteroidradar.api.NasaApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModel( private val database: AsteroidDatabase, application: Application) : AndroidViewModel(application) {

    private val mainRepo = MainRepo(database)

    private val _navigateToAsteroidDetail = MutableLiveData<Asteroid>()
    val navigateToAsteroidDetail: LiveData<Asteroid>
        get() = _navigateToAsteroidDetail

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    init {
       if( checknetwork(application.applicationContext) ) // handle no internet connectivity
       {
           viewModelScope.launch {
               refreshPictureOfDay()
               mainRepo.getAsteroids()
               Log.i("MainViewModel","init")
           }
       }else{
           Toast.makeText(application.applicationContext,"Sorry,no internet connectivity, connect to internet then restart the app to see the image of the day",Toast.LENGTH_LONG).show();
       }
    }

    val asteroidsList = mainRepo.asteroids // assign data from repo to trigger observer

    private fun checknetwork(context: Context): Boolean {
            val info =
                (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                    .activeNetworkInfo
        return info !=null
    }



    fun onAsteroidClicked(asteroid: Asteroid){
        _navigateToAsteroidDetail.value = asteroid
    }

    fun onAsteroidDetailNavigated(){
        _navigateToAsteroidDetail.value = null
    }

    private suspend fun refreshPictureOfDay() {
        withContext(Dispatchers.IO) {
            try {
                _pictureOfDay.postValue(
                    NasaApi.retrofitService.getPictureOfTheDay().await()
                )
            } catch (err: Exception) {
                Log.e("refreshPictureOfDay", Log.getStackTraceString(err))
            }
        }
    }
}

