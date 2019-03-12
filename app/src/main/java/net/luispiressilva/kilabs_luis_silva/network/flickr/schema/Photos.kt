package net.luispiressilva.kilabs_luis_silva.network.flickr.schema

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import net.luispiressilva.kilabs_luis_silva.model.PhotoFlickr

/**
 * Created by Luis Silva on 07/03/2019.
 */
@JsonClass(generateAdapter = true)
data class Photos(
    @Json(name = "page") val page: Int,
    @Json(name = "pages") val pages: Int,
    @Json(name = "perpage") val perpage: Int,
    @Json(name = "total") val total: Int,
    @Json(name = "photo") val list: List<PhotoFlickr>
)