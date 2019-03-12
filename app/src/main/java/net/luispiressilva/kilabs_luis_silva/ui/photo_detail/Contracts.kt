package net.luispiressilva.kilabs_luis_silva.ui.photo_detail

/**
 * Created by Luis Silva on 28/01/2019.
 */
interface Contracts {

    interface IFlickrPhotoDetailView {
        fun setPhotoMetaData(metadata: String, error: Boolean)
    }

    interface IFlickrPhotoDetailPresenter {
        fun start(id: String)
        fun getPhotoMetaData(id: String)
    }


}