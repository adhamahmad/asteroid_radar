package com.udacity.asteroidradar

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DatabaseAsteroidDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg asteroid: DatabaseAsteroid)

    @Query("SELECT * FROM database_asteroids WHERE closeApproachDate >= :today  ORDER BY closeApproachDate ")
    fun getAsteroids(today:String): LiveData<List<DatabaseAsteroid>>
}