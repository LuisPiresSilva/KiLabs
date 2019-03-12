package net.luispiressilva.kilabs_luis_silva.network.flickr

import io.reactivex.Single
import io.reactivex.disposables.Disposable
import net.luispiressilva.kilabs_luis_silva.components.AppSchedulers
import net.luispiressilva.kilabs_luis_silva.network.ErrorType
import net.luispiressilva.kilabs_luis_silva.network.flickr.schema.ServerResponse
import net.luispiressilva.kilabs_luis_silva.network.flickr.schema.metadata.PhotoResponse
import net.luispiressilva.kilabs_luis_silva.network.networkError
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Luis Silva on 06/03/2019.
 */
open class FlickrRemoteDataSource @Inject constructor(private val api: FlickrAPI, private val sub: AppSchedulers) {

    //im not providing ways to other layer to pass extras
    //these are fixed and i think these are important to be part of the PhotoFlickr model
    //would need better study the api to see if i should allow other layers to pass extras
    //and create a enum with the extra's available to pass
    private val url_c = "url_c"
    private val url_o = "url_o"
    private val date_taken = "date_taken"


    //does not accept sorting
    private fun apiGetRecent(): Single<Response<ServerResponse>> {
        return api.getRecent("$url_c,$url_o,$date_taken")
    }

    //accepts sorting
    private fun apiGetSearch(search: String): Single<Response<ServerResponse>> {
        return api.getSearch("$url_c,$url_o,$date_taken", search)
    }


    private fun apiGetPhotoMetaData(id: String): Single<Response<PhotoResponse>> {
        return api.getExif(id)
    }


    fun getRecentPhotos(category: String, result: DataSourceContracts.GetRecent): Disposable? {
        return apiGetRecent()
            .subscribeOn(sub.io)
            .observeOn(sub.android)
            .subscribe({
                when {
                    it.isSuccessful -> {
                        result.flickrPhotosSuccess(category, it.body()!!)
                    }

                    else -> {

                        //DEVELOPER_NOTE
                        /* i did not implemented this part but goal is to create networkError or error object and populate
                        it for the different scenarios (also present/described in FlickrAPI as comments):
                        -   server returns 404 for this request -> ok something is definitely wrong, does not make sense
                            maybe based on this context a dialog showing the error message
                            instead of the typical not found (which for user would not make sense)
                            (something is wrong with our server we are trying to fix it... would be the error
                             message besides the server probably returning a not found message)
                        -   another usefulness would potentially be to add localization context to these error messages
                            (in case no localization param is never sent to server we could deal with it in the client)
                            i prefer to potentially set the string resource id in this error object than having to
                            deal with error codes in other layers (but i also put them anyway)
                        */
                        when (it.code()) {
                            in 400..600 -> {
                                //prep networkError to pass on
                            }

                        }

                        result.flickrPhotosError(category, networkError(it.code(), ErrorType.SERVER, "", null))
                    }
                }

            }, {
                result.flickrPhotosError(category, handleThrowable(it))
            })
    }


    fun getSearchPhotos(category: String, search: String, result: DataSourceContracts.GetSearch): Disposable? {
        return apiGetSearch(search)
            .subscribeOn(sub.io)
            .observeOn(sub.android)
            .subscribe({
                when {
                    it.isSuccessful -> {
                        result.flickrPhotosSuccess(category, it.body()!!)
                    }
                    else -> {

                        when (it.code()) {
                            in 400..600 -> {
                                //prep data to pass on
                            }
                        }

                        result.flickrPhotosError(category, networkError(it.code(), ErrorType.SERVER, "", null))
                    }
                }

            }, {
                result.flickrPhotosError(category, handleThrowable(it))
            })
    }

    fun getPhotoMetaData(result: DataSourceContracts.Photo, id: String): Disposable? {
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


    private fun handleThrowable(throwable: Throwable): networkError {
        when (throwable.cause) {
            is IOException -> {
                //here maybe we should send a Non Fatal to somewhere (like crashlytics)
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