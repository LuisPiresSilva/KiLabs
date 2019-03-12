package net.luispiressilva.kilabs_luis_silva


import net.luispiressilva.kilabs_luis_silva.base.BaseTest
import net.luispiressilva.kilabs_luis_silva.base.MockServer
import net.luispiressilva.kilabs_luis_silva.network.flickr.FlickrAPI
import net.luispiressilva.kilabs_luis_silva.network.flickr.schema.ServerResponse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


/**
 * Created by Luis Silva on 08/03/2019.
 */

@RunWith(JUnit4::class)
class ModelNetworkSchemaTest : BaseTest(){

    override fun isMockServerEnabled(): Boolean = true

    private lateinit var dataSource: FlickrAPI


    @Before
    override fun setUp(){
        super.setUp()
        dataSource =  this.testAppComponent.injectFlickrAPI()
    }


    //for testing potential complex structures and data types
    @Test
    fun getRecent_Parser() {
        // Prepare data
        this.mockHttpResponse(MockServer.Requests.getRECENT_SUCESS)

        val response = dataSource.getRecent("")
        var server : ServerResponse? = null
        response.subscribe({
            server = it.body()
        }, {

        })

//        val expectServerResponse = ServerResponse()

//        assert(expectServerResponse == server)

    }


//    @Test
//    fun getPhotos_Parser() {
//
//    }

}


