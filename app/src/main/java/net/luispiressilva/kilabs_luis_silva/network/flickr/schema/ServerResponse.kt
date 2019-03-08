package net.luispiressilva.kilabs_luis_silva.network.flickr.schema

import com.google.gson.annotations.SerializedName

/**
 * Created by Luis Silva on 07/03/2019.
 */
data class ServerResponse (
    @SerializedName("photos") val photos: Photos
)