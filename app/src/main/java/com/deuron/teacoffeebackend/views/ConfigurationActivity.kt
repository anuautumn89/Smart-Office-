package com.deuron.teacoffeebackend.views

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import com.deuron.teacoffeebackend.GlobalVariables
import com.deuron.teacoffeebackend.R
import com.dpitl.deuron.meetingroomt3g.my_classes.text_to_speech.user_sessions.UserSessionManager
import kotlinx.android.synthetic.main.activity_configuration.*
import java.util.regex.Pattern

class ConfigurationActivity : AppCompatActivity() {

    private lateinit var etIpAddress: EditText
    private lateinit var  etPort: EditText
    private lateinit var etMqttPassword:EditText
    private lateinit var etServerUserName:EditText
    private lateinit var etServerPassword:EditText
    private lateinit var etServerURL:EditText

    private lateinit var loutButtonSet: LinearLayout
    private  var dlgMsg: String? = null
    internal var category = ""
    internal var serverIP = ""
    internal var port = ""
    internal lateinit var ipClasses: Array<String>
    internal var context: Context = this
    internal var sharedPref: SharedPreferences? = null
    internal var editor: SharedPreferences.Editor? = null
    internal var userSessionManager: UserSessionManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration)
        sharedPref = this.context.getSharedPreferences(this.context.getString(R.string.app_name), Context.MODE_PRIVATE)
        editor = sharedPref!!.edit()
        userSessionManager = UserSessionManager(context)
        userSessionManager!!.isUserLogin = true

        etIpAddress = findViewById(R.id.etIpAddress)
        etIpAddress.setSelection(etIpAddress.text.length)
        etPort = findViewById(R.id.et_port)
        etPort.setSelection(etPort.text.length)
        etMqttPassword = findViewById(R.id.etServerPassword)
        etMqttPassword.setSelection(etMqttPassword.text.length)
        etServerUserName = findViewById(R.id.etServerUserName)
        etServerUserName.setSelection(etServerUserName.text.length)
        etServerPassword = findViewById(R.id.etServerPassword)
        etServerPassword.setSelection(etServerPassword.text.length)
        etServerURL = findViewById(R.id.etServerURL)
        etServerURL.setSelection(etServerURL.text.length)

        val PARTIAl_IP_ADDRESS = Pattern.compile("^((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9])\\.){0,3}" + "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9])){0,1}$")
        etIpAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (PARTIAl_IP_ADDRESS.matcher(s).matches()) {
                    serverIP = s.toString()
                } else {
                    s.replace(0, s.length, serverIP)
                }

                ipClasses = serverIP.split("\\.".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                if (ipClasses.size == 4 && ipClasses[3].length > 0) {
                    bnExit.setTextColor(resources.getColor(R.color.bgColor))
                    bnExit.isEnabled = true
                } else {
                    bnExit.setTextColor(resources.getColor(R.color.colorGray))
                    bnExit.isEnabled = false
                }
            }
        })
    }

    fun okBtnClicked(view: View) {
        serverIP = etIpAddress.text.toString()
        port = etPort.text.toString()
        var serverUname = etServerUserName.toString()
        var serverPassword = etServerPassword.toString()
        var mqttPassword = etMqttPassword.toString()
        var serverURL_ = etServerURL.toString()
        editor!!.putBoolean("isFirstRun", false)
        editor!!.putString("ServerIP", serverIP)
        editor!!.putString("serverUserName",serverUname)
        editor!!.putString("serverPassword",serverPassword)
        editor!!.putString("mqttPassword",mqttPassword)
        editor!!.putString("serverURL",serverURL_)
        editor!!.putString("Port",port)
        editor!!.commit()
        login()
    }
    fun login(){
        val activity = context as Activity
        activity.finish()
        val intent = Intent(context, LoginUserActivity::class.java)
        context.startActivity(intent)
    }
    fun cancelButtonClick(view: View) {
        GlobalVariables.exiting = true
        finish()
    }
    override fun onPause() {
        super.onPause()
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}