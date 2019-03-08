package net.luispiressilva.kilabs_luis_silva.base

import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import java.io.File
import java.net.HttpURLConnection

/**
 * Created by Luis Silva on 20/02/2019.
 */
class MockServer {


    enum class Requests {
        getRECENT_SUCESS
    }

    private fun getJson(path : String) : String {
        val uri = javaClass.classLoader.getResource( "mocked_server_responses" + File.separatorChar + path)
        val file = File(uri.path)
        return String(file.readBytes())
    }

    fun simulate(req : Requests, server : MockWebServer) {
        when (req) {
            Requests.getRECENT_SUCESS -> {server.enqueue(MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(getJson("getRecent_Success.json")))
            }
        }
    }
}