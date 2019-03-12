package net.luispiressilva.kilabs_luis_silva.ui.main

import io.reactivex.disposables.CompositeDisposable
import net.luispiressilva.kilabs_luis_silva.DOGS
import net.luispiressilva.kilabs_luis_silva.KITTENS
import net.luispiressilva.kilabs_luis_silva.PUBLIC_FEED
import net.luispiressilva.kilabs_luis_silva.network.flickr.DataSourceContracts
import net.luispiressilva.kilabs_luis_silva.network.flickr.FlickrRemoteDataSource
import net.luispiressilva.kilabs_luis_silva.network.flickr.schema.ServerResponse
import net.luispiressilva.kilabs_luis_silva.network.networkError
import net.luispiressilva.kilabs_luis_silva.ui.base.BasePresenter
import javax.inject.Inject

//'open' in order to be able to mock this class for instrumentation testing
open class MainViewModel @Inject constructor(private val flickrRemoteDataSource: FlickrRemoteDataSource) :
    BasePresenter<Contracts.IFlickrPhotosView>(),
    Contracts.IFlickrPhotosPresenter,
    DataSourceContracts.GetRecent,
    DataSourceContracts.GetSearch {


    //any disposables we use here should be added here
    private val disposables: CompositeDisposable = CompositeDisposable()

    //viewmodel survives rotations and such, so we dispose here
    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }


    //holds the data of each category inside the controller
    var categoryMap: HashMap<String, CategoryViewController> = HashMap()

    private fun init(category: String) {
        categoryMap[category] = CategoryViewController()
    }


    override fun getCategoryPhotos(category: String) {
        if (categoryMap.containsKey(category)) {
            categoryMap[category]?.let {
                if (it.list.isEmpty() && it.state != CategoryViewController.UIState.LOADING) {
                    it.state = CategoryViewController.UIState.LOADING
                    view()?.showLoading(category)
                }
            }


            flickrRemoteDataSource.let {
                when (category) {
                    KITTENS -> categoryMap[category]?.fetcher = it.getSearchPhotos(category, KITTENS, this)
                    DOGS -> categoryMap[category]?.fetcher = it.getSearchPhotos(category, DOGS, this)
                    PUBLIC_FEED -> categoryMap[category]?.fetcher = it.getRecentPhotos(category, this)
                }
                categoryMap[category]?.fetcher?.run {
                    disposables.add(this)
                }
            }
        }
    }


    //keeps old state in case of configuration change or other
    override fun start(category: String) {
        if (!categoryMap.containsKey(category)) {
            init(category)
        }
        categoryMap[category]?.let {
            when (it.state) {
                //instead of a simpler '!::fetcher.isInitialized' check we must null check the fetcher var
                //RXjava does not handle well the lateinit later in unit testing
                CategoryViewController.UIState.LOADING -> {
                    if (it.fetcher == null || it.fetcher?.isDisposed == true) {
                        getCategoryPhotos(category)
                    } else {
                        //already fetching
                    }
                }
                CategoryViewController.UIState.CONTENT -> view()?.showPhotos(category, it.list)
                CategoryViewController.UIState.CONTENT_ERROR -> view()?.showContentError(category, it.error?.message + "")
                CategoryViewController.UIState.NOCONTENT -> view()?.showNoContent(category)
                CategoryViewController.UIState.NOCONTENT_ERROR -> view()?.showNoContentError(category, it.error?.message + "")

            }
        }
    }


    override fun reset(category: String) {
        disposables.clear()
        init(category)
        start(category)
    }


    //we add the new data, confirm/change state to showing content and dispatch data to views
    override fun flickrPhotosSuccess(category: String, response: ServerResponse) {
        categoryMap[category]?.let {

            it.list.addAll(response.photos.list)

            if(it.list.isEmpty()){
                it.state = CategoryViewController.UIState.NOCONTENT
                view()?.showNoContent(category)
            } else {
                it.state = CategoryViewController.UIState.CONTENT
                view()?.showPhotos(category, it.list)
            }
        }
    }


    override fun flickrPhotosError(category: String, error: networkError) {
        if(categoryMap[category]?.list?.isEmpty()!!){
            categoryMap[category]?.state = CategoryViewController.UIState.NOCONTENT_ERROR
            view()?.showNoContentError(category, error.message + "")
        } else {
            categoryMap[category]?.state = CategoryViewController.UIState.CONTENT_ERROR
            view()?.showContentError(category, error.message + "")
        }

    }



}
