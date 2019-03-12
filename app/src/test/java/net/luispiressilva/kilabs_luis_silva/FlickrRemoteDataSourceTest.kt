package net.luispiressilva.kilabs_luis_silva

import net.luispiressilva.kilabs_luis_silva.base.BaseTest
import net.luispiressilva.kilabs_luis_silva.base.MockServer
import net.luispiressilva.kilabs_luis_silva.helpers.any
import net.luispiressilva.kilabs_luis_silva.network.flickr.FlickrRemoteDataSource
import net.luispiressilva.kilabs_luis_silva.network.flickr.DataSourceContracts
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

/**
 * Created by Luis Silva on 08/03/2019.
 */
class FlickrRemoteDataSourceTest : BaseTest() {

    override fun isMockServerEnabled(): Boolean = true

    @Mock
    private lateinit var recentResult: DataSourceContracts.GetRecent

    private lateinit var dataSource: FlickrRemoteDataSource


    @Before
    override fun setUp() {
        super.setUp()
        dataSource = this.testAppComponent.injectFlickrRemoteDataSource()
    }


    @Test
    fun getRecent_whenSuccess() {
        // Prepare data
        this.mockHttpResponse(MockServer.Requests.getRECENT_SUCESS)

        dataSource.getRecentPhotos("", recentResult)

        //flickrPhotosSuccess gets called and only once
        Mockito.verify(recentResult, Mockito.only()).flickrPhotosSuccess(any(), any())


    }


}