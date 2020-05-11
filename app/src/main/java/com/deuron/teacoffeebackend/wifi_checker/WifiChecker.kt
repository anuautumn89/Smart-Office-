@file:Suppress("DEPRECATION")

package com.deuron.teacoffeebackend.wifi_checker

import android.Manifest
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.deuron.teacoffeebackend.GlobalVariables

public  class WifiChecker {

    internal lateinit var context: Context
    internal lateinit var sharedpreferences: SharedPreferences
    internal var autoConnect = true
    internal var sb = StringBuilder()
    internal var selectedWifiList: String? = null
    internal var wifiArray: Array<String>? = null

    fun checkingWifi(mContext: Context) {
        context = mContext
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE)
        autoConnect = sharedpreferences.getBoolean("AutoConnectOn", true)//"true" is the default value.
        selectedWifiList = sharedpreferences.getString("SelectedWifi", "")
        connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager

        if (autoConnect) {
            wifiManager!!.isWifiEnabled = true
            Thread(Task(context, wifiManager!!)).start()
        }

    }

    fun checkingWifiState(context: Context): Boolean {
        connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return connMgr!!.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting
    }

    fun getConfiguredNetworks(mManager: WifiManager): Unit? {
        try {
            return if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null
            }else {}
            mManager.configuredNetworks
        } catch (e: Exception) {
            return null
        }

    }

    internal inner class Task(var context: Context, var wifiManager: WifiManager) : Runnable {

        override fun run() {
            Looper.prepare()
            try {
                Thread.sleep(5000)
                doLater(context, wifiManager)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            Looper.loop()
        }
    }

    fun doLater(context: Context, wifiManager: WifiManager): Boolean {
        var mIsWifi = connMgr!!.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting
        if (mIsWifi) {
            var mySSID = ""
            sb = StringBuilder()
            val results = wifiManager.scanResults
            for (i in results.indices) {
                mySSID = wifiManager.scanResults[i].SSID.toString()
                if (selectedWifiList!!.contains(mySSID)) {
                    //                    Toast.makeText(context, mySSID + " Wifi network is available", Toast.LENGTH_LONG).show();
                    GlobalVariables.loopEnabled = false
                    GlobalVariables.isConnect = true
                    GlobalVariables.tryAgain = true
                    mIsWifi = true
                    break
                } else {
                    if (i == results.size - 1) {
                        wifiManager.isWifiEnabled = false
                        mIsWifi = false
                    }
                }
            }
        } else {
            wifiManager.isWifiEnabled = false
            mIsWifi = false
        }
        return mIsWifi
    }

    companion object {
        val MyPREFERENCES = "MyPrefs"
        var connMgr: ConnectivityManager? = null
        var wifiManager: WifiManager? = null
    }
}
