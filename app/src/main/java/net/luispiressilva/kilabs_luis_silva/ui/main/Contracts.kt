package net.luispiressilva.kilabs_luis_silva.ui.main

import net.luispiressilva.kilabs_luis_silva.model.PhotoFlickr

/**
 * Created by Luis Silva on 28/01/2019.
 */
interface Contracts {

    interface IFlickrPhotosView {
        fun showLoading(category: String)
        fun showPhotos(category: String, photos: List<PhotoFlickr>)
        fun showNoContent(category: String)
        fun showNoContentError(category: String, error: String)
        fun showContentError(category: String, error: String)
        fun addCategory(category: String)
        fun isSortByDate(category: String) : Boolean
    }

    interface IFlickrPhotosPresenter {
        fun start(category: String)
        fun reset(category: String)
        fun getCategoryPhotos(category: String)
        fun orderByDate(category: String, isOrder : Boolean)
    }


}