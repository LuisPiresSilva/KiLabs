package net.luispiressilva.kilabs_luis_silva.di.modules.datasource

import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import net.luispiressilva.kilabs_luis_silva.components.AppSchedulers
import net.luispiressilva.kilabs_luis_silva.network.flickr.FlickrAPI
import net.luispiressilva.kilabs_luis_silva.network.flickr.FlickrRemoteDataSource
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Luis Silva on 14/02/2019.
 */
@Module
class DataSourceModule {

    @Provides
    @Singleton
    fun provideAppsDataSource(api: FlickrAPI) = FlickrRemoteDataSource(api, AppSchedulers())

    @Provides
    @Singleton
    @Named("TESTING")
    fun provideAppsTestDataSource(api: FlickrAPI, sub : AppSchedulers) = FlickrRemoteDataSource(api, sub)


    @Provides
    @Singleton
    fun provideSchedulers() : AppSchedulers {
        return AppSchedulers(Schedulers.trampoline(), Schedulers.trampoline(), Schedulers.trampoline())
    }



}

