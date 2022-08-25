package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AsteroidDao {
    @Query("select * from asteroid_table")
    fun getAsteroids(): LiveData<List<DatabaseAsteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: DatabaseAsteroid)

    @Query("SELECT * FROM asteroid_table WHERE close_approach_date = :todayDay ORDER BY close_approach_date ASC ")
    fun getTodayAsteroids(todayDay: String): LiveData<List<DatabaseAsteroid>>

    @Query("SELECT * FROM asteroid_table WHERE close_approach_date BETWEEN :startDate AND :endDate ORDER BY close_approach_date ASC")
    fun getWeekAsteroids(startDate: String, endDate: String): LiveData<List<DatabaseAsteroid>>
}

@Database(entities = [DatabaseAsteroid::class], version = 1)
abstract class AsteroidsDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
}

private lateinit var INSTANCE: AsteroidsDatabase

fun getDatabase(context: Context): AsteroidsDatabase {
    synchronized(AsteroidsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                AsteroidsDatabase::class.java,
                "asteroids").build()
        }
    }
    return INSTANCE
}