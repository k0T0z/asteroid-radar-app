package com.udacity.asteroidradar.repository

import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.MainActivity
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.database.DatabaseAsteroid
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.network.NASAApiFilter
import com.udacity.asteroidradar.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

private const val START_DATE = "2022-08-24"
private const val END_DATE = "2022-08-24"

class AsteroidsRepository(private val database: AsteroidsDatabase) {

    private val calendar: Calendar = Calendar.getInstance()

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroids()) {
            it.asDomainModel()
        }

    suspend fun refreshAsteroids(
        filter: NASAApiFilter
    ) {

        withContext(Dispatchers.IO) {
//            val startDate: String
//            val endDate: String
//            if(filter == NASAApiFilter.SHOW_WEEK) {
//                calendar.set(Calendar.DAY_OF_WEEK, 1)
//                val startYear: Int = calendar.get(Calendar.YEAR)
//                val startMonth: Int = calendar.get(Calendar.MONTH)+1
//                val startDay: Int = calendar.get(Calendar.DAY_OF_MONTH)
//                calendar.set(Calendar.DAY_OF_WEEK, 7)
//                val endYear: Int = calendar.get(Calendar.YEAR)
//                val endMonth: Int = calendar.get(Calendar.MONTH)+1
//                val endDay: Int = calendar.get(Calendar.DAY_OF_MONTH)
//                val startLocalDate: LocalDate = LocalDate.of(startYear,startMonth,startDay)
//                val endLocalDate: LocalDate = LocalDate.of(endYear,endMonth,endDay)
//                startDate = startLocalDate.format(DateTimeFormatter. ofPattern(Constants.API_QUERY_DATE_FORMAT))
//                endDate = endLocalDate.format(DateTimeFormatter. ofPattern(Constants.API_QUERY_DATE_FORMAT))
//            }
//            else {
//                val localDate: LocalDate = LocalDate.now()
//                startDate = localDate.format(DateTimeFormatter. ofPattern(Constants.API_QUERY_DATE_FORMAT))
//                endDate = startDate
//            }
            try {
                val response = Network.nasa.getResponse(START_DATE, END_DATE, Constants.KEY)
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
            catch (e: Exception) {

            }
        }
    }
}

