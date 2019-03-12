package net.luispiressilva.kilabs_luis_silva.base

/**
 * Created by Luis Silva on 08/03/2019.
 */

import android.content.Context
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import net.luispiressilva.kilabs_luis_silva.FLICKR_URL
import net.luispiressilva.kilabs_luis_silva.di.DaggerTestAppComponent
import net.luispiressilva.kilabs_luis_silva.di.TestAppComponent
import net.luispiressilva.kilabs_luis_silva.di.modules.datasource.DataSourceModule
import net.luispiressilva.kilabs_luis_silva.di.modules.network.NetworkModule
import net.luispiressilva.kilabs_luis_silva.di.modules.network.OkHttpClientModule
import org.junit.After
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.io.File


abstract class BaseTest {

    @Mock
    private lateinit var context: Context

    lateinit var testAppComponent: TestAppComponent
    lateinit var mockServer: MockWebServer


    @Before
    open fun setUp(){
        MockitoAnnotations.initMocks(this)
        this.configureMockServer()
        this.configureDi()
    }

    @After
    open fun tearDown(){
        this.stopMockServer()
    }


    //assembleAndroidTest or compileDebugSource might be needed to be run so that
    // Dagger generates the code and 'DaggerTestAppComponent' gets resolved
    // CONFIGURATION
    open fun configureDi(){
        this.testAppComponent = DaggerTestAppComponent.builder()
            .applicationContext(context)
            .useCache(OkHttpClientModule.UseCache(false))
            .host(NetworkModule.Host(if (isMockServerEnabled()) mockServer.url("/").toString() else FLICKR_URL))
            .networkModule(NetworkModule())
            .dataSourceModule(DataSourceModule())
            .build()
    }

    // MOCK SERVER
    abstract fun isMockServerEnabled(): Boolean // Because we don't want it always enabled on all tests

    open fun configureMockServer(){
        if (isMockServerEnabled()){
            mockServer = MockWebServer()
            mockServer.start()
        }
    }

    open fun stopMockServer() {
        if (isMockServerEnabled()){
            mockServer.shutdown()
        }
    }

    open fun mockHttpResponse(fileName: String, responseCode: Int) = mockServer.enqueue(MockResponse()
        .setResponseCode(responseCode)
        .setBody(getJson(fileName)))

    open fun mockHttpResponse(type : MockServer.Requests) = MockServer().simulate(type, mockServer)

    private fun getJson(path : String) : String {
        val uri = javaClass.classLoader.getResource( "mocked_server_responses" + File.separatorChar + path)
        val file = File(uri.path)
        return String(file.readBytes())
    }
}