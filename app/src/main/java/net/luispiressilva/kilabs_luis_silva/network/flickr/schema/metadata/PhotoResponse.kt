package net.luispiressilva.kilabs_luis_silva.network.flickr.schema.metadata

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Luis Silva on 09/03/2019.
 */

@JsonClass(generateAdapter = true)
class PhotoResponse(
    @Json(name = "stat")
    val stat: String,
    @Json(name = "code")
    var code: Int = 0,
    @Json(name = "message")
    var message: String = "",
    @Json(name = "photo")
    val photo: PhotoMetaData
) {
}