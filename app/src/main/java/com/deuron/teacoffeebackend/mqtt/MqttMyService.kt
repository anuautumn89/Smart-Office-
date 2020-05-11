package com.deuron.teacoffeebackend.mqtt

import android.R
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.deuron.teacoffeebackend.GlobalVariables
import com.deuron.teacoffeebackend.fragments.MyItemRecyclerViewAdapter
import com.deuron.teacoffeebackend.models.orderList.OrderResponse
import com.deuron.teacoffeebackend.views.MainActivity
import com.deuron.teacoffeebackend.views.MainActivity.Companion.mqttServerImage
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.json.JSONObject
import java.lang.Double
import java.text.SimpleDateFormat
import java.util.*


class MqttMyService(private var context: Context) : Service(), MqttCallbackExtended {
    internal var df: SimpleDateFormat
    internal var tf: SimpleDateFormat
    private var str: Array<String>? = null
    private var msg: Array<String>? = null
    private var arrIrValue: Array<String>? = null
    private var irIndex: Int = 0
    internal lateinit var devType: String
    internal lateinit var devName: String
    internal var keyValue: String? = null
    internal var ambTemp: String? = null
    internal  lateinit var strMsg: String
    internal lateinit var lastSentAcValue: String
    internal var jsonMsg: JSONObject? = null
     private lateinit var messageOnarriveListner: MqttMessageHelper
    init {
        GlobalVariables.waiting = false
        df = SimpleDateFormat("dd/MM/yy")
        tf = SimpleDateFormat("hh:mm:ss")
    }
    override fun connectionLost(cause: Throwable) {
        Log.d("MQTT_CONNECTION", "Connection Lost")
        try {
            networkUnreachable()
        } catch (ex: Exception) {
            println(ex.message.toString())
        }
    }

    private fun networkUnreachable() {
        GlobalVariables.exiting = false
        GlobalVariables.waiting = true
        try {
                DrawableCompat.setTint(mqttServerImage!!.drawable, ContextCompat.getColor(context!!, R.color.holo_red_dark))
        } catch (e: Exception) {
            println(e.message.toString())
        }
    }
     interface MqttMessageHelper {
         fun messageOnarrive()
     }

    @Throws(Exception::class)
    override fun messageArrived(topic: String, message: MqttMessage) {
        Log.i("all_msgs ", "Topic: $topic, Mqtt Message: $message")
        //TODO --->properly initiate the listner
        var mainAct = MainActivity()
        mainAct.messageOnarrive()
        // messageOnarriveListner.messageOnarrive()
//        try {
//            var apiCall = ApiCalls(context)
//            apiCall.getOrderList(6,  object : ResponseCallback<String?> {
//                override fun onFailure(errorResponse: String?) {
//                    /* Check API status and returns the error */
//                    SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
//                        .setTitleText("API Error - Order list ")
//                        .setContentText(errorResponse)
//                        .show()
//                }
//                override fun onSuccess(response: String?) {
//                    Log.d("",response)
//
//                }
//            })
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
    }
     override fun deliveryComplete(token: IMqttDeliveryToken) {
        //        System.out.println("msg delivered");
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    fun isDouble(str: String): Boolean {
        try {
            Double.parseDouble(str)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override fun connectComplete(reconnect: Boolean, serverURI: String) {
        try {
            Log.d("MQTT_CONNECTION", "Connection Complete")
               DrawableCompat.setTint(mqttServerImage!!.drawable, ContextCompat.getColor(context!!, android.R.color.holo_green_light))
        } catch (e: Exception) {
            println(e.message.toString())
        }
    }
}
