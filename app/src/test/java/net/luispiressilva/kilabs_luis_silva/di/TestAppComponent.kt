package net.luispiressilva.kilabs_luis_silva.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import net.luispiressilva.kilabs_luis_silva.components.viewmodel.ViewModelFactory
import net.luispiressilva.kilabs_luis_silva.di.modules.datasource.DataSourceModule
import net.luispiressilva.kilabs_luis_silva.di.modules.network.NetworkModule
import net.luispiressilva.kilabs_luis_silva.di.modules.network.OkHttpClientModule
import net.luispiressilva.kilabs_luis_silva.di.modules.viewmodel.ViewModelModule
import net.luispiressilva.kilabs_luis_silva.network.flickr.FlickrAPI
import net.luispiressilva.kilabs_luis_silva.network.flickr.FlickrRemoteDataSource
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Luis Silva on 08/03/2019.
 */
@Singleton
@Component(modules = [NetworkModule::class, DataSourceModule::class, ViewModelModule::class])
interface TestAppComponent {

    @Named("TESTING")
    fun injectFlickrRemoteDataSource() : FlickrRemoteDataSource


    fun injectFlickrAPI() : FlickrAPI

    fun inject() : ViewModelFactory


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(applicationContext: Context) : Builder

        @BindsInstance
        fun useCache(useCache : OkHttpClientModule.UseCache) : Builder

        @BindsInstance
        fun host(host : NetworkModule.Host) : Builder

        @BindsInstance
        fun networkModule(network : NetworkModule) : Builder

        @BindsInstance
        fun dataSourceModule(host : DataSourceModule) : Builder

        fun build(): TestAppComponent
    }

}