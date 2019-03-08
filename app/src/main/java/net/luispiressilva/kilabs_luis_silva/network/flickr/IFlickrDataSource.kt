package net.luispiressilva.kilabs_luis_silva.network.flickr

import net.luispiressilva.kilabs_luis_silva.network.flickr.schema.ServerResponse
import net.luispiressilva.kilabs_luis_silva.network.networkError


/**
 * Created by Luis Silva on 07/03/2019.
 */
interface IFlickrDataSource {
    fun flickrPhotosSuccess(category: String, response: ServerResponse)
    fun flickrPhotosError(category: String, error: networkError)
}