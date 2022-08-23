package com.udacity.asteroidradar.network

import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.DatabaseAsteroid
import org.json.JSONObject

@JsonClass(generateAdapter = true)
data class NetworkAsteroidContainer(val asteroids: String)

// TODO create a data object for json retrieved

fun NetworkAsteroidContainer.asDatabaseModel(): Array<DatabaseAsteroid> {
    val asteroidsList = parseAsteroidsJsonResult(JSONObject(asteroids))

    return asteroidsList.map {
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
}