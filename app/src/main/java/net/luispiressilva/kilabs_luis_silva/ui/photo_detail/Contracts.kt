package net.luispiressilva.kilabs_luis_silva.ui.photo_detail

import net.luispiressilva.kilabs_luis_silva.model.PhotoFlickr

/**
 * Created by Luis Silva on 28/01/2019.
 */
interface Contracts {

    interface IFlickrPhotoDetailView {
        fun setPhotoMetaData(metadata : String)
        fun showNoContentError(error: String)
    }

    interface IFlickrPhotoDetailPresenter {
        fun start(id : String)
        fun getPhotoMetaData(id : String)
    }


}