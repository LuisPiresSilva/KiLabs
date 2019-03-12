package net.luispiressilva.kilabs_luis_silva.network.flickr.schema.metadata

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Raw(
    @Json(name = "raw")
    val raw: String = "",
    @Json(name = "clean")
    var clean: String = ""
) {

}
