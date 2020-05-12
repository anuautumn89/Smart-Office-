package com.deuron.teacoffeebackend.views

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import cn.pedant.SweetAlert.SweetAlertDialog
import com.deuron.teacoffeebackend.GlobalVariables
import com.deuron.teacoffeebackend.R
import com.deuron.teacoffeebackend.api.api_manager.ApiCalls
import com.deuron.teacoffeebackend.api.api_manager.ResponseCallback
import com.deuron.teacoffeebackend.fragments.EmptyMessage
import com.deuron.teacoffeebackend.fragments.OrderItemFragment
import com.deuron.teacoffeebackend.fragments.TeaCoffeeDisplay
import com.deuron.teacoffeebackend.models.orderList.OrderResponse
import com.deuron.teacoffeebackend.mqtt.MqttMyService
import com.deuron.teacoffeebackend.wifi_checker.WifiChecker
import com.dpitl.deuron.meetingroomt3g.my_classes.text_to_speech.user_sessions.UserSessionManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.language_screen.view.*
import kotlinx.android.synthetic.main.language_screen.view.lutCloseLngScreen
import kotlinx.android.synthetic.main.notification_settings.*
import kotlinx.android.synthetic.main.notification_settings.view.*
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import java.util.*
class MainActivity : AppCompatActivity(), OrderItemFragment.OnItemSelectedListener,
    TeaCoffeeDisplay.orderDetailOnClickListner, MqttMyService.MqttMessageHelper {
    private lateinit var fragmentContainer:FrameLayout
    private var sharedPref: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    internal var context: Context = this
    private var currentLanguage: String = "si"
    private var client: MqttAndroidClient? = null
    private val mqttConnectOptions = MqttConnectOptions()
    private var brokerUrl: String? = null
    private var gv = GlobalVariables(context)
    private var token: IMqttToken? = null
    private val mqttUsername = "admin"
    private val mqttPassword = "admin"
    private lateinit var teaCoffeeFragment:TeaCoffeeDisplay
    private lateinit var orderItemFragment:OrderItemFragment
    private lateinit var emptyMessageFrag:EmptyMessage
    private var userSessionManager: UserSessionManager? = null
    private var wifiChecker: WifiChecker? = null
    internal var isWifi: Boolean? = null
    private lateinit var dialog: ProgressDialog
    private var isSoundOn:Boolean = true
    private var isVibrationOn:Boolean =true
    private var uid: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wifiChecker = WifiChecker()
        dialog = ProgressDialog(context as Activity)
        sharedPref = this.context.getSharedPreferences(this.context.getString(R.string.app_name), Context.MODE_PRIVATE)
        editor = sharedPref!!.edit()
        currentLanguage = sharedPref!!.getString("CURRENT_LANGUAGE", "en").toString()
        val ip = sharedPref!!.getString("ServerIP", "0.0.0.0")!!.split(":")
        var ipAddress = ip[0]
        var serverURL = "http://$ipAddress/deuron_automation_api_cloud/v1.0/api/"
        GlobalVariables.ApiBaseUrl = serverURL
        //getOrdersAPI()
        //setLocale(currentLanguage)
        brokerUrl = "tcp://192.168.1.4:1883"
        client = gv.getMqttClient(brokerUrl!!)
        userSessionManager = UserSessionManager(context)
        uid = userSessionManager!!.uId.toInt()
       // getOrders(false)
        mqttConnectOptions.mqttVersion = MqttConnectOptions.MQTT_VERSION_3_1_1
        mqttConnectOptions.isCleanSession = true
        mqttConnectOptions.isAutomaticReconnect = true
        mqttConnectOptions.keepAliveInterval = 30
        if (mqttUsername != "") mqttConnectOptions.userName = mqttUsername
        if (mqttPassword != "") mqttConnectOptions.password = mqttPassword.toCharArray()
        fragmentContainer = findViewById(R.id.fragmentContainer)
        //subscribeTopics()
        //TODO REMOVE LATER
        temp()
    }
    fun getOrders(flag:Boolean){
        orderItemFragment = OrderItemFragment.newInstance(1,"",currentLanguage,context)
        emptyMessageFrag = EmptyMessage.newInstance()
        if(flag){
            //Loading
        }
        var apiCall = ApiCalls(context)
        apiCall.getOrderList(uid,  object : ResponseCallback<String?> {
            override fun onFailure(errorResponse: String?) {
                /* Check API status and returns the error */
                SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("API Error - Order list ")
                    .setContentText(errorResponse)
                    .show()
            }
            override fun onSuccess(response: String?) {
                if(!GlobalVariables.OrderResponseArrList!!.isEmpty()){
                    loadFragment(orderItemFragment)
                }else {
                    loadFragment(emptyMessageFrag)
                }
            }
        })
    }
    private fun loadFragment(fragment:Fragment){
        val fragmentManager = supportFragmentManager
        fragmentContainer = findViewById(R.id.fragmentContainer)
        val transaction = fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment)
        transaction.commit()
    }
    private fun loadLanguages(view: View) {
        //Inflate the dialog with custom view
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.language_screen, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("")
        //show dialog
        //mDialogView.dialogEmailEt.setSelection(mDialogView.dialogEmailEt.text.length)
        val  mAlertDialog = mBuilder.show()
        mDialogView.btnEnglish.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
            setLocale("en");
        }
        mDialogView.btnSinhala.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
            setLocale("si");
        }
        mDialogView.btnTamil.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
            setLocale("tl");
        }
        mDialogView.lutCloseLngScreen.setOnClickListener{
            mAlertDialog.dismiss()
        }
    }
    private fun setLocale(localeName: String) {
            var locale = Locale(localeName)
            val res: Resources = resources
            val dm: DisplayMetrics = res.displayMetrics
            val conf: Configuration = res.configuration
            conf.locale = locale
            res.updateConfiguration(conf, dm)
            val refresh = Intent(this, MainActivity::class.java)
            editor!!.putString("CURRENT_LANGUAGE", localeName)
            editor!!.commit()
            //refresh.putExtra(currentLanguage, localeName)
            startActivity(refresh)
    }
    private fun subscribeTopics(){
        try {
            token = client!!.connect(mqttConnectOptions)
            token!!.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    // We are connected
                    Log.d("MQTT_CONNECTION", "onSuccess")
                    DrawableCompat.setTint(mqttServerImage!!.drawable, ContextCompat.getColor(context!!, android.R.color.holo_green_dark))
                    subscribeToTopic()
                }
                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    networkUnreachable()
                    Log.d("MQTT_CONNECTION", "onFailure")
                }
            }
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }
    private fun subscribeToTopic() {
        try {
            client!!.subscribe(GlobalVariables.subTopic, 0, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    Log.i("Sub", "Subscribed!")
                }
                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    Log.i("Sub", "Failed to subscribe")
                }
            })
        } catch (ex: MqttException) {
            ex.printStackTrace()
        }

    }
    private fun networkUnreachable() {
        try {
            GlobalVariables.exiting = false
            GlobalVariables.waiting = true
            DrawableCompat.setTint(mqttServerImage!!.drawable, ContextCompat.getColor(context!!, android.R.color.holo_red_dark))
            client!!.connect(mqttConnectOptions)
        } catch (e: Exception) {
        }
    }

    companion object {
        var mqttServerImage: ImageView? = null
        private var UIHandler: Handler
        internal lateinit var orderItemFragment:OrderItemFragment
        var currentLanguage: String = "en"
        init {
            UIHandler = Handler(Looper.getMainLooper())
        }

        fun runOnUI(runnable: Runnable) {
            UIHandler.post(runnable)
        }
    }
    /*Set Notification*/
    fun notificationSettings(view: View) {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.notification_settings, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("")
        val  mAlertDialog = mBuilder.show()
        mDialogView.bnOK.setOnClickListener{
            isSoundOn =  mDialogView.cbSound.isChecked
            isVibrationOn = mDialogView.cbVibration.isChecked
            mAlertDialog.dismiss()
        }
        mAlertDialog.bnCancel.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }

    //List Item OnClick event (Send data from fragment to host activity)
    override fun onItemClicked(position: Int, orderResponse: OrderResponse?) {
       // var teaCoffeeDisplay = TeaCoffeeDisplay.newInstance(context, orderResponse)
       // loadFragment(teaCoffeeDisplay)
    }
    //TODO ---TEMP USE
    fun temp(){
        var orderRes = OrderResponse()
        var teaCoffeeDisplay = TeaCoffeeDisplay.newInstance(context)
        loadFragment(teaCoffeeDisplay)
    }
    //Order updated /order item closed
    override fun onButtonClicked(action:String) {
        emptyMessageFrag = EmptyMessage.newInstance()
        orderItemFragment = OrderItemFragment.newInstance(1,"",currentLanguage,context)
        if(GlobalVariables.OrderResponseArrList!!.isNotEmpty()){
            loadFragment(orderItemFragment)
        }else {
            loadFragment(emptyMessageFrag)
        }
    }
    //TODO ---- THIS IS WHERE NOTIFICATION POPUP HAPPEN ----> MQTT MESSAGE ON ARRIVAL
    override fun messageOnarrive() {
        SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
            .setTitleText("Order Request")
            .setContentText("An order arrived !")
            .show()
       getOrders(false)
    }

    fun reloadDataSet(view: View) {
        getOrders(true)
    }
}
