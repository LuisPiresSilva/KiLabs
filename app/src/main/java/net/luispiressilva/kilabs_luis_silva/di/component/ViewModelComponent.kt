package net.luispiressilva.kilabs_luis_silva.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import net.luispiressilva.kilabs_luis_silva.di.modules.datasource.DataSourceModule
import net.luispiressilva.kilabs_luis_silva.di.modules.network.NetworkModule
import net.luispiressilva.kilabs_luis_silva.di.modules.network.OkHttpClientModule
import net.luispiressilva.kilabs_luis_silva.di.modules.viewmodel.ViewModelModule
import net.luispiressilva.kilabs_luis_silva.network.flickr.FlickrAPI
import net.luispiressilva.kilabs_luis_silva.ui.main.MainFragment
import net.luispiressilva.kilabs_luis_silva.ui.photo_detail.PhotoDetailFragment
import javax.inject.Singleton

/**
 * Created by Luis Silva on 06/03/2019.
 */
@Singleton
@Component(modules = [ViewModelModule::class, DataSourceModule::class, NetworkModule::class])
interface ViewModelComponent {

    fun inject(frag: MainFragment)
    fun inject(frag: PhotoDetailFragment)

    fun inject(): FlickrAPI

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(applicationContext: Context): Builder

        @BindsInstance
        fun useCache(useCache: OkHttpClientModule.UseCache): Builder

        @BindsInstance
        fun host(host: NetworkModule.Host): Builder

        fun build(): ViewModelComponent
    }

}