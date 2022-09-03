package com.udacity.asteroidradar

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseAsteroid::class], version = 2, exportSchema = false)
abstract class AsteroidDatabase :RoomDatabase(){
abstract val databaseAsteroidDao: DatabaseAsteroidDao
companion object{
    @Volatile
    private var INSTANCE: AsteroidDatabase? = null

    fun getInstance(context: Context): AsteroidDatabase{ // singleton pattern
        synchronized(this){
            var  instance = INSTANCE

            if(instance == null){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AsteroidDatabase::class.java,
                    "asteroids_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
            }
            return instance
        }
    }
}

}