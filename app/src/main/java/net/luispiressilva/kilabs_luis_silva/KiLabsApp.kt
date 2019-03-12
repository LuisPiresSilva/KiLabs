package net.luispiressilva.kilabs_luis_silva

import android.app.Application
import net.luispiressilva.kilabs_luis_silva.helpers.Utils
import timber.log.Timber

/**
 * Created by Luis Silva on 06/03/2019.
 */

class KiLabsApp : Application() {

    companion object {
        lateinit var app: KiLabsApp

    }

    fun init() {
        app = this

        Utils(app)


    }


    override fun onCreate() {
        super.onCreate()
        init()


        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}