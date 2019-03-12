package net.luispiressilva.kilabs_luis_silva.di.modules.network

import dagger.Module
import dagger.Provides
import net.luispiressilva.kilabs_luis_silva.network.flickr.FlickrAPI
import retrofit2.Retrofit

/**
 * Created by Luis Silva on 05/10/2018.
 */
@Module(includes = [RetrofitModule::class])
class NetworkModule {

    class Host(val host: String)

    @Provides
    fun provideRetrofitService(builder: Retrofit.Builder, baseUrl: Host): FlickrAPI {
        return builder.baseUrl(baseUrl.host).build().create(FlickrAPI::class.java)
    }


}