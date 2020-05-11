package com.deuron.teacoffeebackend.views

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import cn.pedant.SweetAlert.SweetAlertDialog
import com.deuron.teacoffeebackend.GlobalVariables
import com.deuron.teacoffeebackend.R
import com.deuron.teacoffeebackend.api.api_manager.ApiCalls
import com.deuron.teacoffeebackend.api.api_manager.ResponseCallback
import com.deuron.teacoffeebackend.interfaces.ApiCallInterface
import com.deuron.teacoffeebackend.wifi_checker.WifiChecker
import com.dpitl.deuron.meetingroomt3g.my_classes.text_to_speech.user_sessions.UserSessionManager

class LoginUserActivity : AppCompatActivity() {
    internal var context: Context = this
    internal lateinit var userName: String
    internal lateinit var password: String
    internal var gv = GlobalVariables(context)
    private lateinit var dialog: ProgressDialog
    internal lateinit var etUname: EditText
    internal lateinit var etPassword: EditText
    private var wifiChecker: WifiChecker? = null
    internal var isWifi: Boolean? = null
    lateinit var img_keep_loggedin: ImageView
    private var userSessionManager: UserSessionManager? = null
    //private var errorMessage: ErrorMessage? = null
    private var sharedPref: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private  var keepLoggedInStatus: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_user)
        wifiChecker = WifiChecker()
        dialog = ProgressDialog(context as Activity)
        userSessionManager = UserSessionManager(context)
        etUname = findViewById<View>(R.id.etUname) as EditText
        etUname.setSelection(etUname.text.length)
        etPassword = findViewById<View>(R.id.etPwd) as EditText
        etPassword.setSelection(etPassword.text.length)
        img_keep_loggedin = findViewById(R.id.img_keep_loggedin)
        sharedPref = this.context.getSharedPreferences(this.context.getString(R.string.app_name), Context.MODE_PRIVATE)
        editor = sharedPref!!.edit()
        val ip = sharedPref!!.getString("ServerIP", "0.0.0.0")!!.split(":")
        var ipAddress = ip[0]
        var serverURL = "http://$ipAddress/deuron_automation_api_cloud/v1.0/api/"
        GlobalVariables.ApiBaseUrl = serverURL
    }

    fun login(view: View) = try {
        val pDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
        var gatewayId = sharedPref?.getString(GlobalVariables.GATEWAY_ID,null)
        userName = etUname.text.toString().trim()
        password = etPassword.text.toString().trim()
        var apiCall = ApiCalls(context)
        Handler().postDelayed({
            pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
            pDialog.titleText = "please wait.."
            pDialog.setCancelable(true)
            pDialog.show()
            apiCall.userLogin(
                userName,
                password,
                object : ResponseCallback<String?> {
                    override fun onSuccess(response: String?) {
                        if (gatewayId == null) {
                            val intent = Intent(context, GatewayActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            intent.putExtra("OPENING_ACTIVITY", true)
                            pDialog.dismiss()
                            context.startActivity(intent)
                        } else {
                            pDialog.dismiss()
                            val intent = Intent(context, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            intent.putExtra("OPENING_ACTIVITY", true)
                            pDialog.dismiss()
                            context.startActivity(intent)
                        }
                    }
                    override fun onFailure(errorResponse: String?) {
                        pDialog.dismiss()
                        /* Check API status and returns the error */
                        SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Login Error")
                            .setContentText(errorResponse)
                            .show()
                    }
                })
        },1000)
    } catch (e: Exception) {
        SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
            .setTitleText("System Error")
            .setContentText(e.message)
            .show()
    }

    fun rememberPassword(view: View) {
        if(!userSessionManager!!.isKeepLoggin){
            userSessionManager!!.isKeepLoggin = true
            DrawableCompat.setTint(img_keep_loggedin.drawable, ContextCompat.getColor(context!!, android.R.color.holo_blue_light))
        } else {
            userSessionManager!!.isKeepLoggin = false
            DrawableCompat.setTint(img_keep_loggedin.drawable, ContextCompat.getColor(context!!, android.R.color.white))
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }

}