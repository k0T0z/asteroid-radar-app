package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch


class MainViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val asteroidsRepository = AsteroidsRepository(database)

    init {
        viewModelScope.launch {
            asteroidsRepository.refreshAsteroids()
        }
    }

    val asteroidsList = asteroidsRepository.asteroids

    private val _navigateToAsteroidDetails = MutableLiveData<Long>()
    val navigateToAsteroidDetails
        get() = _navigateToAsteroidDetails


    fun onAsteroidClicked(id: Long){
        _navigateToAsteroidDetails.value = id
    }

    fun onAsteroidDetailsNavigated() {
        _navigateToAsteroidDetails.value = null
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