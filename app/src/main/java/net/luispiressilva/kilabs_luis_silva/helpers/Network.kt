package net.luispiressilva.kilabs_luis_silva.helpers

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


/**
 * Created by Luis Silva on 08/10/2018.
 */
class NetworkUtils(internal val app: Application) {


    fun hpHasNetwork(): Boolean {
        return hpHasNetwork(app)
    }

}


//normal error in order to use this we must add (android.permission.ACCESS_NETWORK_STATE) permission in the manifest
@SuppressLint("MissingPermission")
fun hpHasNetwork(context: Context): Boolean {
    val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
    return networkInfo?.isConnected == true
}



