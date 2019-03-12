package net.luispiressilva.kilabs_luis_silva.di.modules.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


/**
 * Created by Luis Silva on 05/10/2018.
 */
@Module(includes = [OkHttpClientModule::class])
class RetrofitModule {

    @Provides
    @Singleton
    fun retrofit(okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder().also { build(it, okHttpClient) }
    }


    private fun build(builder: Retrofit.Builder, okHttpClient: OkHttpClient) {
//        val adapters = GsonBuilder()
//            .registerTypeAdapter()
//            .create()

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        builder
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
    }
}