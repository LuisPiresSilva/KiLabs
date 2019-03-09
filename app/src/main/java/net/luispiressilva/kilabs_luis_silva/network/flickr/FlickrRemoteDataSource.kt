package net.luispiressilva.kilabs_luis_silva.network.flickr

import io.reactivex.Single
import io.reactivex.disposables.Disposable
import net.luispiressilva.kilabs_luis_silva.components.AppSchedulers
import net.luispiressilva.kilabs_luis_silva.network.ErrorType
import net.luispiressilva.kilabs_luis_silva.network.flickr.schema.ServerResponse
import net.luispiressilva.kilabs_luis_silva.network.flickr.schema.metadata.PhotoResponse
import net.luispiressilva.kilabs_luis_silva.network.networkError
import net.luispiressilva.kilabs_luis_silva.ui.photo_detail.Contracts
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Luis Silva on 06/03/2019.
 */
open class FlickrRemoteDataSource @Inject constructor(private val api: FlickrAPI, private val sub: AppSchedulers) {

    //does not accept sorting
    private fun apiGetRecent(extras : String): Single<Response<ServerResponse>> {
        return api.getRecent("url_c,url_o,date_taken")
    }

    //accepts sorting
    private fun apiGetSearch(extras : String, search : String): Single<Response<ServerResponse>> {
        return api.getSearch(extras, search)
    }



    private fun apiGetPhotoMetaData(id : String): Single<Response<PhotoResponse>> {
        return api.getExif(id)
    }





    fun getRecentPhotos(category: String, result: DataSourceContracts.GetRecent): Disposable? {
        return apiGetRecent("")
            .subscribeOn(sub.io)
            .observeOn(sub.android)
            .subscribe({
                when {
                    it.isSuccessful -> {
                        Timber.i(it.body()!!.toString())
                        result.flickrPhotosSuccess(category, it.body()!!)
                    }

                    else -> {

                        //DEVELOPER_NOTE
                        /* i did not implemented this part but my thoughts would be something like
                        having a ServerError object with codes, message etc (typical server response stuff)
                        (which we populate here and pass on)
                        but also more helpful methods for instance lets say:
                        -   server returns 404 for this request -> ok something is definitely wrong, does not make sense
                            maybe based on that context a dialog showing the error message
                            instead of the typical not found (which for user would not make sense)
                            (something is wrong with our server we are trying to fix it...)
                        -   another usefulness would potentially be to add localization context to these error messages
                            (in case no localization param is never sent to server we could deal with it in the client)
                        */
                        when (it.code()) {
                            in 400..600 -> {
                                //prep data to pass on
                            }

                        }

                        result.flickrPhotosError(category, networkError(it.code(), ErrorType.SERVER, "", null))
                    }
                }

            }, {
                handleThrowable(it)


                result.flickrPhotosError(category, handleThrowable(it))
                //here maybe we should send a Non Fatal to somewhere (like crashlytics)
//                error(Error(it))
            })
    }


    fun getPhotoMetaData(result: DataSourceContracts.Photo, id : String): Disposable? {
        return apiGetPhotoMetaData(id)
            .subscribeOn(sub.io)
            .observeOn(sub.android)
            .subscribe({
                when {
                    it.isSuccessful ->
                        result.flickrPhotoSuccess(it.body()!!)

                    else -> {

                        when (it.code()) {
                            in 400..600 -> {
                                //prep data to pass on
                            }

                        }

                        result.flickrPhotoError(networkError(it.code(), ErrorType.SERVER, "", null))
                    }
                }

            }, {
                result.flickrPhotoError(handleThrowable(it))

            })
    }



    private fun handleThrowable(throwable : Throwable) : networkError {
        System.out.println("Start Test: 3 - " + " - " + throwable.cause + " - " + throwable.message)
        when (throwable.cause) {
            is IOException -> {
                return networkError(0, ErrorType.ERROR, throwable.message + "", throwable)
            }
            is IllegalStateException -> {
                //here maybe we should send a Non Fatal to somewhere (like crashlytics)
                return networkError(0, ErrorType.ERROR, throwable.message + "", throwable)
            }
            else -> {
                //here maybe we should send a Non Fatal to somewhere (like crashlytics)
                return networkError(0, ErrorType.ERROR, "UNKNOWN", throwable)
            }
        }
    }

}