package net.luispiressilva.kilabs_luis_silva

import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import net.luispiressilva.kilabs_luis_silva.model.PhotoFlickr
import net.luispiressilva.kilabs_luis_silva.network.ErrorType
import net.luispiressilva.kilabs_luis_silva.network.flickr.DataSourceContracts
import net.luispiressilva.kilabs_luis_silva.network.flickr.FlickrRemoteDataSource
import net.luispiressilva.kilabs_luis_silva.network.flickr.schema.Photos
import net.luispiressilva.kilabs_luis_silva.network.flickr.schema.ServerResponse
import net.luispiressilva.kilabs_luis_silva.network.networkError
import net.luispiressilva.kilabs_luis_silva.ui.main.CategoryViewController
import net.luispiressilva.kilabs_luis_silva.ui.main.Contracts
import net.luispiressilva.kilabs_luis_silva.ui.main.MainViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


/**
 * Created by Luis Silva on 29/01/2019.
 */

@RunWith(JUnit4::class)
class MainViewModelTest {


    lateinit var dataSource: FlickrRemoteDataSource

    @Mock
    lateinit var result: DataSourceContracts

    @Mock
    lateinit var view: Contracts.IFlickrPhotosView


    @InjectMocks
    private lateinit var presenter: MainViewModel


    val catTest = PUBLIC_FEED

    @Before
    fun setup() {

        dataSource = mock(FlickrRemoteDataSource::class.java)

        presenter = MainViewModel(dataSource)

        MockitoAnnotations.initMocks(this)

    }


    /*
     * schema mock builder
     */
    private fun prepSchemaModel(photoList: List<PhotoFlickr>): ServerResponse {
        val photos = Photos(1, 10, 100, 1000, photoList)
        val serverResponse = ServerResponse(photos, "")

        return serverResponse
    }


    /*
     * makes response preparation much easier and readable
     */
    private fun <T> setResponse_CallBack(obj: T, func: () -> Unit): Disposable {
        return Single.just(obj)
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())
            .subscribe({
                func()
            }, {
                error(Error(it))
            })
    }



    @Test
    fun whenSuccessShowPhotos() {

        val photosList: ArrayList<PhotoFlickr> = ArrayList()

        val photo = PhotoFlickr("1")
        photosList.add(photo)

        val serverResponse = prepSchemaModel(photosList)



        whenever(dataSource.getRecentPhotos(catTest, presenter)).then {
            setResponse_CallBack(serverResponse) { presenter.flickrPhotosSuccess(catTest, serverResponse) }
        }

        presenter.start(catTest)


        verify(view, Mockito.atLeastOnce()).showPhotos(Mockito.anyString(), eq(photosList))
    }


    @Test
    fun when_initialStart_data_isEmpty_then_ServerReturns_Success_EmptyList() {

        val photosList: List<PhotoFlickr> = emptyList()

        val serverResponse = prepSchemaModel(photosList)

        //INITIAL STATE
        assert(presenter.categoryMap.isEmpty())


        //FLOW
        whenever(dataSource.getRecentPhotos(catTest, presenter)).then {
            setResponse_CallBack(serverResponse) { presenter.flickrPhotosSuccess(catTest, serverResponse) }
        }
        presenter.start(catTest)


        //FINAL STATE
        assert(!presenter.categoryMap.isEmpty())

        assert(presenter.categoryMap[catTest]?.list?.isEmpty()!!)
        verify(view, Mockito.never()).showLoading(Mockito.anyString())
        verify(view, Mockito.atLeastOnce()).showNoContent(Mockito.anyString())
        assert(presenter.categoryMap[catTest]?.state == CategoryViewController.UIState.NOCONTENT)
    }


    @Test
    fun when_initialStart_data_isEmpty_then_ServerReturns_Success_ValidData() {

        val photosList: ArrayList<PhotoFlickr> = ArrayList()

        val photo = PhotoFlickr("1")
        photosList.add(photo)

        val serverResponse = prepSchemaModel(photosList)

        //INITIAL STATE
        assert(presenter.categoryMap.isEmpty())


        //FLOW
        whenever(dataSource.getRecentPhotos(catTest, presenter)).then {
            setResponse_CallBack(serverResponse) { presenter.flickrPhotosSuccess(catTest, serverResponse) }
        }
        presenter.start(catTest)


        //FINAL STATE
        assert(!presenter.categoryMap.isEmpty())
        assert(!presenter.categoryMap[catTest]?.list?.isEmpty()!!)

        verify(view, Mockito.never()).showLoading(Mockito.anyString()) //loading is already showing (default)
        verify(view, Mockito.atLeastOnce()).showPhotos(Mockito.anyString(), eq(photosList))
        assert(presenter.categoryMap[catTest]?.state == CategoryViewController.UIState.CONTENT)
    }


    @Test
    fun when_initialStart_data_isEmpty_then_ServerReturns_LocalError() {

        val photosList: ArrayList<PhotoFlickr> = ArrayList()

        val photo = PhotoFlickr("1")
        photosList.add(photo)

        val serverResponse = prepSchemaModel(photosList)

        val error = networkError(0, ErrorType.ERROR, "", null)

        //INITIAL STATE
        assert(presenter.categoryMap.isEmpty())

        //FLOW
        whenever(dataSource.getRecentPhotos(catTest, presenter)).then {
            setResponse_CallBack(serverResponse) { presenter.flickrPhotosError(catTest, error) }
        }
        presenter.start(catTest)


        //FINAL STATE
        assert(presenter.categoryMap[catTest]?.list?.isEmpty() == true)

        verify(view, Mockito.never()).showLoading(Mockito.anyString()) //loading is already showing (default)
        verify(view, Mockito.atLeastOnce()).showNoContentError(Mockito.anyString(), Mockito.anyString())
        assert(presenter.categoryMap[catTest]?.state == CategoryViewController.UIState.NOCONTENT_ERROR)
    }


    @Test
    fun when_initialStart_data_isEmpty_then_ServerReturns_ServerError() {

        val photosList: ArrayList<PhotoFlickr> = ArrayList()

        val photo = PhotoFlickr("1")
        photosList.add(photo)

        //not used just dummy to pass we control callbacks
        val serverResponse = prepSchemaModel(photosList)

        val error = networkError(404, ErrorType.SERVER, "", null)

        //INITIAL STATE
        assert(presenter.categoryMap.isEmpty())


        //FLOW
        whenever(dataSource.getRecentPhotos(catTest, presenter)).then {
            setResponse_CallBack(serverResponse) { presenter.flickrPhotosError(catTest, error) }
        }
        presenter.start(catTest)


        //FINAL STATE
        assert(!presenter.categoryMap.isEmpty())
        assert(presenter.categoryMap[catTest]?.list?.isEmpty() == true)
        verify(view, Mockito.never()).showLoading(Mockito.anyString()) //loading is already showing (default)
        verify(view, Mockito.atLeastOnce()).showNoContentError(Mockito.anyString(), Mockito.anyString())
        assert(presenter.categoryMap[catTest]?.state == CategoryViewController.UIState.NOCONTENT_ERROR)
    }


    @Test
    fun when_presentData_isValid_then_ServerReturns_LocalError() {

        val photosList: ArrayList<PhotoFlickr> = ArrayList()

        val photo = PhotoFlickr("1")
        photosList.add(photo)

        val serverResponse = prepSchemaModel(photosList)

        val error = networkError(0, ErrorType.ERROR, "", null)


        //INITIAL STATE
        assert(presenter.categoryMap.isEmpty())

        //FLOW - adds some data
        whenever(dataSource.getRecentPhotos(catTest, presenter)).then {
            setResponse_CallBack(serverResponse) { presenter.flickrPhotosSuccess(catTest, serverResponse) }
        }
        presenter.start(catTest)


        //FINAL STATE
        assert(presenter.categoryMap[catTest]?.state == CategoryViewController.UIState.CONTENT)


        //SECOND STATE
        assert(!presenter.categoryMap.isEmpty())


        //FLOW
        whenever(dataSource.getRecentPhotos(catTest, presenter)).then {
            setResponse_CallBack(serverResponse) { presenter.flickrPhotosError(catTest, error) }
        }
        presenter.getCategoryPhotos(catTest)


        //FINAL STATE
        assert(!presenter.categoryMap[catTest]?.list?.isEmpty()!!)

        verify(view, Mockito.never()).showLoading(Mockito.anyString()) //loading is already showing (default)
        verify(view, Mockito.atLeastOnce()).showContentError(Mockito.anyString(), Mockito.anyString())
        assert(presenter.categoryMap[catTest]?.state == CategoryViewController.UIState.CONTENT_ERROR)
    }





    @Test
    fun when_presentData_isValid_then_ServerReturns_ServerError() {

        val photosList: ArrayList<PhotoFlickr> = ArrayList()

        val photo = PhotoFlickr("1")
        photosList.add(photo)

        val serverResponse = prepSchemaModel(photosList)

        val error = networkError(404, ErrorType.SERVER, "", null)


        //INITIAL STATE
        assert(presenter.categoryMap.isEmpty())

        //FLOW - adds some data
        whenever(dataSource.getRecentPhotos(catTest, presenter)).then {
            setResponse_CallBack(serverResponse) { presenter.flickrPhotosSuccess(catTest, serverResponse) }
        }
        presenter.start(catTest)


        //FINAL STATE
        assert(presenter.categoryMap[catTest]?.state == CategoryViewController.UIState.CONTENT)


        //SECOND STATE
        assert(!presenter.categoryMap.isEmpty())


        //FLOW
        whenever(dataSource.getRecentPhotos(catTest, presenter)).then {
            setResponse_CallBack(serverResponse) { presenter.flickrPhotosError(catTest, error) }
        }
        presenter.getCategoryPhotos(catTest)


        //FINAL STATE
        assert(presenter.categoryMap[catTest]?.list?.isEmpty() == false)

        verify(view, Mockito.never()).showLoading(Mockito.anyString()) //loading is already showing (default)
        verify(view, Mockito.atLeastOnce()).showContentError(Mockito.anyString(), Mockito.anyString())
        assert(presenter.categoryMap[catTest]?.state == CategoryViewController.UIState.CONTENT_ERROR)
    }


    @Test
    fun when_presentData_EmptyorEmptyError_then_ShowLoadingUponRequest() {

        val photosList: ArrayList<PhotoFlickr> = ArrayList()

        val photo = PhotoFlickr("1")
        photosList.add(photo)

        val serverResponse = prepSchemaModel(photosList)

        //we set state to error but could be any empty/no data state (depends exercise does not tells what to do)
        //INITIAL STATE
        presenter.categoryMap[catTest] = CategoryViewController()
        presenter.categoryMap[catTest]?.state = CategoryViewController.UIState.NOCONTENT_ERROR
        assert(presenter.categoryMap[catTest]?.list?.isEmpty()!!)
        assert(presenter.categoryMap[catTest]?.state == CategoryViewController.UIState.NOCONTENT_ERROR)

        //FLOW
        whenever(dataSource.getRecentPhotos(catTest, presenter)).then {
            setResponse_CallBack(serverResponse) { presenter.flickrPhotosSuccess(catTest, serverResponse) }
        }
        presenter.getCategoryPhotos(catTest)


        //FINAL STATE
        assert(presenter.categoryMap[catTest]?.state == CategoryViewController.UIState.CONTENT)
        assert(!presenter.categoryMap[catTest]?.list?.isEmpty()!!)


        //ERROR -> LOADING -> CONTENT
        verify(view, Mockito.atLeastOnce()).showLoading(Mockito.anyString())
        verify(view, Mockito.atLeastOnce()).showPhotos(Mockito.anyString(), eq(photosList))
    }
}