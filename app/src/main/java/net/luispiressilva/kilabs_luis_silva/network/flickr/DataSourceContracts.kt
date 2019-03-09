package net.luispiressilva.kilabs_luis_silva.network.flickr

import net.luispiressilva.kilabs_luis_silva.network.flickr.schema.ServerResponse
import net.luispiressilva.kilabs_luis_silva.network.flickr.schema.metadata.PhotoResponse
import net.luispiressilva.kilabs_luis_silva.network.networkError

/**
 * Created by Luis Silva on 08/03/2019.
 */
interface DataSourceContracts {

    interface GetRecent {
        fun flickrPhotosSuccess(category: String, response: ServerResponse)
        fun flickrPhotosError(category: String, error: networkError)
    }

    interface GetSearch {
        fun flickrPhotosSuccess(category: String, response: ServerResponse)
        fun flickrPhotosError(category: String, error: networkError)
    }

    interface Photo {
        fun flickrPhotoSuccess(response: PhotoResponse)
        fun flickrPhotoError(error: networkError)
    }
}