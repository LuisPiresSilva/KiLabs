package net.luispiressilva.kilabs_luis_silva.ui.photo_detail

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import net.luispiressilva.kilabs_luis_silva.network.flickr.DataSourceContracts
import net.luispiressilva.kilabs_luis_silva.network.flickr.FlickrRemoteDataSource
import net.luispiressilva.kilabs_luis_silva.network.flickr.schema.metadata.PhotoResponse
import net.luispiressilva.kilabs_luis_silva.network.networkError
import net.luispiressilva.kilabs_luis_silva.ui.BasePresenter
import java.net.URI
import javax.inject.Inject

class PhotoDetailViewModel @Inject constructor(private val flickrRemoteDataSource: FlickrRemoteDataSource) :
    BasePresenter<Contracts.IFlickrPhotoDetailView>(),
    Contracts.IFlickrPhotoDetailPresenter,
    DataSourceContracts.Photo {


    //any disposables we use here should be added here
    private val disposables: CompositeDisposable = CompositeDisposable()

    //viewmodel survives rotations and such, so we dispose here
    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }


    var fetcher: Disposable? = null
    var metadata = ""

    override fun start(id: String) {
        if(metadata.isBlank()){
            getPhotoMetaData(id)
        }
        view()?.setPhotoMetaData(metadata)
    }

    override fun getPhotoMetaData(id: String) {
        flickrRemoteDataSource.let {
            fetcher = it.getPhotoMetaData(this, id)
            disposables.add(fetcher!!)
        }
    }



    override fun flickrPhotoSuccess(response: PhotoResponse) {
        metadata = response.photo.exif.toString()

        view()?.setPhotoMetaData(metadata)
    }

    override fun flickrPhotoError(error: networkError) {
        metadata = "error"

        view()?.setPhotoMetaData(metadata)
    }

}
