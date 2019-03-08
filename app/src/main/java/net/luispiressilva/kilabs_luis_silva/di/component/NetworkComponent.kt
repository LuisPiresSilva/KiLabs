package net.luispiressilva.kilabs_luis_silva.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import net.luispiressilva.kilabs_luis_silva.di.modules.network.NetworkModule
import net.luispiressilva.kilabs_luis_silva.di.modules.network.OkHttpClientModule
import net.luispiressilva.kilabs_luis_silva.network.flickr.FlickrAPI
import javax.inject.Singleton

/**
 * Created by Luis Silva on 05/10/2018.
 */

@Singleton
@Component(modules = [NetworkModule::class])
interface NetworkComponent {

//    fun prepareRetrofitNonCaching(): NetworkWrapper

    fun inject(): FlickrAPI

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(applicationContext: Context) : Builder

        @BindsInstance
        fun useCache(useCache : OkHttpClientModule.UseCache) : Builder

        @BindsInstance
        fun host(host : NetworkModule.Host) : Builder

        fun build(): NetworkComponent
    }
}