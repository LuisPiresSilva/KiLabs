package net.luispiressilva.kilabs_luis_silva.network.flickr.schema.metadata

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Luis Silva on 09/03/2019.
 */

@JsonClass(generateAdapter = true)
class PhotoMetaData (
    @Json(name ="id")
    val id: String,
    @Json(name ="secret")
    var secret: String = "",
    @Json(name ="server")
    var server: String = "",
    @Json(name ="farm")
    var farm: Int = 0,
    @Json(name ="camera")
    var camera: String = "",
    @Json(name ="exif")
    var exif: List<PhotoExif>
) {
}