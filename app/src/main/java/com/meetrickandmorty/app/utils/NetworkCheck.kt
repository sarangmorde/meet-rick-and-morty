package com.meetrickandmorty.app.utils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData


object NetworkCheck : LiveData<Boolean>() {

    private lateinit var application: Application

    private val networkCallback = object : NetworkCallback() {
        override fun onAvailable(network: Network) {
            value = true
        }

        override fun onLost(network: Network) {
            value = false
        }
    }

    fun init(application: Application) {
        NetworkCheck.application = application
    }

    fun isInternetConnected(): Boolean = value ?: false

    override fun onActive() {
        registerBroadCastReceiver()
    }

    override fun onInactive() {
        unRegisterBroadCastReceiver()
    }

    private fun registerBroadCastReceiver() {
        value = try {
            val cm =
                application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkRequest = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()
            cm.registerNetworkCallback(networkRequest, networkCallback)
            false
        } catch (e: Exception) {
            false
        }
    }

    private fun unRegisterBroadCastReceiver() {
        val cm =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.unregisterNetworkCallback(networkCallback)
    }
}
