package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.Asteroid


class MainViewModel(
//    private val database: AsteroidDatabaseDao,
    application: Application
) : AndroidViewModel(application) {
//    val asteroidsList = database.getAllAsteroids()

    private val _navigateToAsteroidDetails = MutableLiveData<Long>()
    val navigateToAsteroidDetails
        get() = _navigateToAsteroidDetails


    fun onAsteroidClicked(id: Long){
        _navigateToAsteroidDetails.value = id
    }

    fun onAsteroidDetailsNavigated() {
        _navigateToAsteroidDetails.value = null
    }
}