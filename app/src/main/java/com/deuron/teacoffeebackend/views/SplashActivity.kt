package com.deuron.teacoffeebackend.views

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.deuron.teacoffeebackend.GlobalVariables
import com.deuron.teacoffeebackend.R
import com.deuron.teacoffeebackend.wifi_checker.WifiChecker
import com.dpitl.deuron.meetingroomt3g.my_classes.text_to_speech.user_sessions.UserSessionManager

class SplashActivity : AppCompatActivity() {
    private val hideHandler = Handler()
    private var context: Context = this
    private var sharedPref: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private var userSessionManager: UserSessionManager? = null
    private var isFullscreen: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        isFullscreen = true
        GlobalVariables.exiting = false
        userSessionManager = UserSessionManager(context)
        sharedPref = this.context.getSharedPreferences(this.context.getString(R.string.app_name), Context.MODE_PRIVATE)
        editor = sharedPref!!.edit()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (GlobalVariables.exiting)
            finish()
        else if (sharedPref!!.getBoolean(GlobalVariables.IS_FIRST_RUN, true)) {
            loadingTeaCoffeeMain()
            //configuration()
        } else {
            if(userSessionManager!!.isKeepLoggin){
                loadingTeaCoffeeMain()
            } else {
                loadingTeaCoffeeMain()
                //login()
            }
        }
    }
    private fun configuration() {
        Handler().postDelayed({
            val intent = Intent(context, ConfigurationActivity::class.java)
            //intent.putExtra(getString(R.string.dlg_msg), getString(R.string.ip_config))
            context.startActivity(intent)
        }, 1000)
    }
    private fun login(){
        val wifiChecker = WifiChecker()
        val isWifi = wifiChecker.checkingWifiState(context)
        if (isWifi) {
            Handler().postDelayed({
                val activity = context as Activity
                activity.finish()
                val intent = Intent(context, LoginUserActivity::class.java)
                context.startActivity(intent)
            }, 1000)
        } else {
            Toast.makeText(context, "No WiFi connection found", Toast.LENGTH_SHORT).show();
            Handler().postDelayed({
//                GlobalVariables.exiting = true
//                val intent = Intent(context, DialogActivity::class.java)
//                intent.putExtra(getString(R.string.dlg_msg), getString(R.string.no_wifi))
//                context.startActivity(intent)
            }, 1000)
        }
    }

    private fun loadingTeaCoffeeMain(){
        Handler().postDelayed({
            val intent = Intent(context, MainActivity::class.java)
            //val intent = Intent(context, TeaCoffeeScreen::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("OPENING_ACTIVITY", true)
            context.startActivity(intent)
        }, 1000)
    }

    override fun onPause() {
        super.onPause()
        //        finish();
    }

    override fun onStop() {
        super.onStop()
        //        finish();
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}