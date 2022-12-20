package com.tanvir.dogbreedapp.service

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
 
class ConnectivityStatus(context: Context) : LiveData<Boolean>() {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
 
    private val networkCallbacks = object : ConnectivityManager.NetworkCallback(){
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            postValue(true)
        }
 
        override fun onLost(network: Network) {
            super.onLost(network)
            postValue(false)
        }
 
        override fun onUnavailable() {
            super.onUnavailable()
            postValue(false)
        }
    }
 
    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkInternet(){
        val network = connectivityManager.activeNetwork
        if(network==null){
            postValue(false)
        }

        val requestBuilder =NetworkRequest.Builder().apply {
            addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) // also for sdk version 23 or above
            addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
        }.build()
 
        connectivityManager.registerNetworkCallback(requestBuilder,networkCallbacks)
    }
 
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActive() {
        super.onActive()
        checkInternet()
    }
 
    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkCallbacks)
    }
}