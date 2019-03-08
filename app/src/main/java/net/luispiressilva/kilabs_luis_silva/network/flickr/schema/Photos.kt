package net.luispiressilva.kilabs_luis_silva.network.flickr.schema

import com.google.gson.annotations.SerializedName
import net.luispiressilva.kilabs_luis_silva.model.PhotoFlickr

/**
 * Created by Luis Silva on 07/03/2019.
 */
data class Photos (
    @SerializedName("page") val page: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("perpage") val perpage: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("photo") val list: List<PhotoFlickr>,
    @SerializedName("stat") val stat: String
)