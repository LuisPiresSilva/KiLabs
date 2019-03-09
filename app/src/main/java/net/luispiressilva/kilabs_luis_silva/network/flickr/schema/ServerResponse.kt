package net.luispiressilva.kilabs_luis_silva.network.flickr.schema

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Luis Silva on 07/03/2019.
 */
@JsonClass(generateAdapter = true)
data class ServerResponse (
    @Json(name ="photos") val photos: Photos,
    @Json(name ="stat") val stat: String
)