package com.udacity.asteroidradar.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.DatabaseAsteroid
import org.json.JSONObject

//@JsonClass(generateAdapter = true)
//data class NetworkPictureOfDayContainer(val picture: NetworkPictureOfDay)
//
//@JsonClass(generateAdapter = true)
//data class NetworkPictureOfDay(
//    val date: String,
//    val explanation: String,
//    val hdurl: String,
//    @Json(name = "media_type")
//    val mediaType: String,
//    val title: String,
//    val url: String,
//)
//
//fun NetworkPictureOfDayContainer.asDomainModel(): PictureOfDay {
//    return PictureOfDay(
//            mediaType = picture.mediaType,
//            title = picture.title,
//            url = picture.url,
//        )
//}
//
//fun NetworkPictureOfDayContainer.asDatabaseModel(): DatabasePictureOfDay {
//    return DatabasePictureOfDay (
//            mediaType = picture.mediaType,
//            title = picture.title,
//            url = picture.url,
//        )
//}