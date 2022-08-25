package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.network.NASAApiFilter
import com.udacity.asteroidradar.network.Network
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val todayCalendar: Calendar = Calendar.getInstance()
    private val weekCalendar: Calendar = Calendar.getInstance()

    private val database = getDatabase(application)
    private val asteroidsRepository = AsteroidsRepository(database)

    init {
        weekCalendar.add(Calendar.DAY_OF_YEAR, 7)
        fetchPictureOfDay()
        viewModelScope.launch {
            asteroidsRepository.refreshAsteroids()
        }
    }

    var asteroidsList = asteroidsRepository.asteroids

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()

    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private fun fetchPictureOfDay() {
        viewModelScope.launch {
            try {
               val response = Network.nasaPictureOfDay.getPictureOfDay()
                _pictureOfDay.value = response
            } catch (e: Exception) {}
        }
    }

    fun filterAsteroidsList(filter: NASAApiFilter) {
        when (filter) {
            NASAApiFilter.SHOW_WEEK -> {
                val startDate: String
                val endDate: String
                val currentTime = todayCalendar.time
                val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
                startDate = dateFormat.format(currentTime)
                val lastTime = weekCalendar.time
                endDate = dateFormat.format(lastTime)
                asteroidsList = Transformations.map(database.asteroidDao.getWeekAsteroids(startDate, endDate)) {
                    it.asDomainModel()
                }
            }
            NASAApiFilter.SHOW_DAY -> {
                val currentTime = todayCalendar.time
                val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
                val todayDate: String = dateFormat.format(currentTime)
                asteroidsList = Transformations.map(database.asteroidDao.getTodayAsteroids(todayDate)) {
                    it.asDomainModel()
                }
            }
            else -> {
                asteroidsList = Transformations.map(database.asteroidDao.getAsteroids()) {
                    it.asDomainModel()
                }
            }

        }
    }

//    class Factory(val app: Application) : ViewModelProvider.Factory {
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
//                @Suppress("UNCHECKED_CAST")
//                return MainViewModel(app) as T
//            }
//            throw IllegalArgumentException("Unable to construct viewmodel")
//        }
//    }
}