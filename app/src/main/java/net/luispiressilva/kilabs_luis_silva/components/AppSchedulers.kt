package net.luispiressilva.kilabs_luis_silva.components

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Luis Silva on 06/03/2019.
 */
class AppSchedulers(
    val io: Scheduler = Schedulers.io(),
    val computation: Scheduler = Schedulers.computation(),
    val android: Scheduler = AndroidSchedulers.mainThread()
)