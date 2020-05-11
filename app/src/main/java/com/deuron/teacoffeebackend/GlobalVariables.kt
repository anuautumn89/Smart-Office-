package com.deuron.teacoffeebackend
import android.content.Context
import android.os.Build
import com.deuron.teacoffeebackend.models.orderList.OrderResponse
import com.deuron.teacoffeebackend.mqtt.MqttMyService
import org.eclipse.paho.android.service.MqttAndroidClient
import java.util.*
class GlobalVariables(internal var context: Context) {

    var clientId = "AND"

    fun getMqttClient(brokerUrl: String): MqttAndroidClient? {
        var mqttClient: MqttAndroidClient? = null
        try {
            mqttClient = MqttAndroidClient(context, brokerUrl, clientId + randomNo)
            mqttClient.setCallback(MqttMyService(context))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return mqttClient
    }

    companion object {
        var OrderResponseArrList: MutableList<OrderResponse>? = mutableListOf()
        var ApiBaseUrl = ""
        val SERVER_URL = "http://192.168.8.10/deuron_automation_api_cloud/"
        val API_BASE = SERVER_URL + "v1.0/api/"
        const val API_KEY = "YXBpX3VzZXI6YXBpX3VzZXIxMjM="
        var subTopic = "node/orders/n2b/+/pending"
        //node/orders/n2b/+/pending
        var gatewayId: String? = null

        var loopEnabled = false
        var isConnect = false
        var tryAgain = false
        var exiting = false
        var waiting = false
        var isGateway = false
        var remeberLoginData = false
        var GATEWAY_ID:String = "gatewayId"
        var IS_FIRST_RUN : String = "isFirstRun"
        var GATEWAY_NAME_EN:String = "gatewayNameEn"
        var GATEWAY_NAME_SI:String = "gatewayNameSi"
        var GATEWAY_NAME_TL:String = "gatewayNameTl"
        var ORDER_EXPIRE_TIME: String = "orderExpireTime"
        private val uuid =  UUID.randomUUID().toString()
        val randomNo: String
            get() = Build.MANUFACTURER + Build.MODEL + uuid
    }
}
