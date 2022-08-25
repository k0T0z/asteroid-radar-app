package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.database.DatabaseAsteroid
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.network.NASAApiFilter
import com.udacity.asteroidradar.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

private const val START_DATE = "2022-08-24"
private const val END_DATE = "2022-08-30"

class AsteroidsRepository(private val database: AsteroidsDatabase) {

    private val calendar: Calendar = Calendar.getInstance()

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroids()) {
            it.asDomainModel()
        }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val startDate: String
            val endDate: String
            val currentTime = calendar.time
            val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
            startDate = dateFormat.format(currentTime)
            calendar.add(Calendar.DAY_OF_YEAR, 7)
            val lastTime = calendar.time
            endDate = dateFormat.format(lastTime)
            try {
                val response = Network.nasa.getResponse(
                    startDate,
                    endDate,
                )
                val asteroidsList = parseAsteroidsJsonResult(JSONObject(response))
                val array = asteroidsList.map {
                    DatabaseAsteroid (
                        id = it.id,
                        codename = it.codename,
                        closeApproachDate = it.closeApproachDate,
                        absoluteMagnitude = it.absoluteMagnitude,
                        estimatedDiameter = it.estimatedDiameter,
                        relativeVelocity = it.relativeVelocity,
                        distanceFromEarth = it.distanceFromEarth,
                        isPotentiallyHazardous = it.isPotentiallyHazardous,
                    )
                }.toTypedArray()
                database.asteroidDao.insertAll(*array)
            }
            catch (e: Exception) {}
        }
    }
}


