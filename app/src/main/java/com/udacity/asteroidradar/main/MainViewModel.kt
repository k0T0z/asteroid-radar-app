package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.network.NASAApiFilter
import com.udacity.asteroidradar.network.Network
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val asteroidsRepository = AsteroidsRepository(database)

    init {
        fetchPictureOfDay()
        viewModelScope.launch {
            asteroidsRepository.refreshAsteroids(NASAApiFilter.SHOW_DAY)
        }
    }

    val asteroidsList = asteroidsRepository.asteroids

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

    fun updateFilter(filter: NASAApiFilter) {
        asteroidsRepository.getAsteroidsRepositoryFiltered(filter)
        viewModelScope.launch {
            asteroidsRepository.refreshAsteroids(filter)
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}