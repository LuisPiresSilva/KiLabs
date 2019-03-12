package net.luispiressilva.kilabs_luis_silva.network.flickr.schema.metadata

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Luis Silva on 09/03/2019.
 */

@JsonClass(generateAdapter = true)
data class PhotoExif(
    @Json(name = "tagspace")
    val tagspace: String,
    @Json(name = "tagspaceid")
    var tagspaceid: Int = 0,
    @Json(name = "tag")
    var tag: String = "",
    @Json(name = "label")
    var label: String = "",
    @Json(name = "raw")
    var raw: Raw? = null
)