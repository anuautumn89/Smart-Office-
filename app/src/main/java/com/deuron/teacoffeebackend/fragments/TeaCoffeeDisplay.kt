package com.deuron.teacoffeebackend.fragments
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import cn.pedant.SweetAlert.SweetAlertDialog
import com.deuron.teacoffeebackend.GlobalVariables
import com.deuron.teacoffeebackend.views.MainActivity.Companion.currentLanguage
import com.deuron.teacoffeebackend.views.MainActivity.Companion.orderItemFragment
import com.deuron.teacoffeebackend.R
import com.deuron.teacoffeebackend.api.api_manager.ApiCalls
import com.deuron.teacoffeebackend.api.api_manager.ResponseCallback
import com.deuron.teacoffeebackend.models.orderList.OrderItemsInfo
import com.deuron.teacoffeebackend.models.orderList.OrderPayload
import com.deuron.teacoffeebackend.models.orderList.OrderResponse
import com.dpitl.deuron.meetingroomt3g.my_classes.text_to_speech.user_sessions.UserSessionManager
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
class TeaCoffeeDisplay : Fragment() {

    private lateinit var lutTeaWithSugar: ConstraintLayout
    private lateinit var lutBTWithOutSugar:ConstraintLayout
    private lateinit var lutMTeaWithSugar:ConstraintLayout
    private lateinit var lutMTWithOutSugar:ConstraintLayout
    private lateinit var lutCWithSugar:ConstraintLayout
    private lateinit var lutCWithOutSugar:ConstraintLayout
    private lateinit var lutMilkWithSugar:ConstraintLayout
    private lateinit var lutMilkWithOutSugar:ConstraintLayout

    private lateinit var btnRequestOrder: Button
    private lateinit var txtBTSCount:TextView
    private lateinit var txtBTNSCount:TextView
    private lateinit var txtMTSCount:TextView
    private lateinit var txtMTNSCount:TextView
    private lateinit var txtCSCount:TextView
    private lateinit var txtCNSCount:TextView
    private lateinit var txtMilkSCount:TextView
    private lateinit var txtMilkNSCount:TextView
    private lateinit var txtTotaItemCount:TextView
    private lateinit var txtDeliveryTime:TextView
    private lateinit var txtOrderNo:TextView
    private lateinit var lutClose:LinearLayout
    private var client: MqttAndroidClient? = null
    private var mqttConnectOptions = MqttConnectOptions()
    private lateinit var orderPayload:OrderPayload
    private var orderId: Int = 0
    private var userSessionManager: UserSessionManager? = null
    private lateinit var mcontext: Context
    private var sharedPref: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private lateinit var fragListner_: orderDetailOnClickListner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tea_coffee_display, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initialize your view here for use view.findViewById("your view id")
        //UI Element initialization

        lutTeaWithSugar = view.findViewById(R.id.lutTeaWithSugar)
        lutBTWithOutSugar = view.findViewById(R.id.lutBTWithOutSugar)
        lutMTeaWithSugar = view.findViewById(R.id.lutMTeaWithSugar)
        lutMTWithOutSugar = view.findViewById(R.id.lutMTWithOutSugar)
        lutCWithSugar = view.findViewById(R.id.lutCWithSugar)
        lutCWithOutSugar = view.findViewById(R.id.lutCWithOutSugar)
        lutMilkWithSugar = view.findViewById(R.id.lutMilkWithSugar)
        lutMilkWithOutSugar = view.findViewById(R.id.lutMilkWithOutSugar)

        txtBTSCount = view.findViewById(R.id.txtBTSCount)
        txtBTNSCount = view.findViewById(R.id.txtBTNSCount)
        txtMTSCount = view.findViewById(R.id.txtMTSCount)
        txtMTNSCount = view.findViewById(R.id.txtMTNSCount)
        txtCSCount = view.findViewById(R.id.txtCSCount)
        txtCNSCount = view.findViewById(R.id.txtCNSCount)
        txtMilkSCount = view.findViewById(R.id.txtMilkSCount)
        txtMilkNSCount = view.findViewById(R.id.txtMilkNSCount)
        txtTotaItemCount = view.findViewById(R.id.txtTotaItemCount)
        txtDeliveryTime = view.findViewById(R.id.txtDeliveryTime)
        txtOrderNo = view.findViewById(R.id.txtOrderNo)
        btnRequestOrder = view.findViewById<Button>(R.id.btnRequestOrder)
        lutClose = view.findViewById(R.id.lutClose)

//        if (orderPayload.getOrderItems()!!.getBLackTeaS() != ""){
//            txtBTSCount.text = orderPayload.getOrderItems()!!.getBLackTeaS()
//            lutTeaWithSugar.visibility = View.VISIBLE
//        }
//        if(orderPayload.getOrderItems()!!.getBlackTeaNs() != ""){
//            txtBTNSCount.text = orderPayload.getOrderItems()!!.getBlackTeaNs()
//            lutBTWithOutSugar.visibility = View.VISIBLE
//        }
//        if (orderPayload.getOrderItems()!!.getMilkTeaS() != ""){
//            txtMTSCount.text = orderPayload.getOrderItems()!!.getMilkTeaS()
//            lutMTeaWithSugar.visibility = View.VISIBLE
//        }
//        if(orderPayload.getOrderItems()!!.getMilkTeaNs() != ""){
//            txtMTNSCount.text = orderPayload.getOrderItems()!!.getMilkTeaNs()
//            lutMTWithOutSugar.visibility = View.VISIBLE
//        }
//        if(orderPayload.getOrderItems()!!.getCoffeeS() != ""){
//            txtCSCount.text = orderPayload.getOrderItems()!!.getCoffeeS()
//            lutCWithSugar.visibility = View.VISIBLE
//        }
//        if (orderPayload.getOrderItems()!!.getCoffeeNs() != ""){
//            txtCNSCount.text = orderPayload.getOrderItems()!!.getCoffeeNs()
//            lutCWithOutSugar.visibility = View.VISIBLE
//        }
//        if(orderPayload.getOrderItems()!!.getMilkS() != ""){
//            txtMilkSCount.text = orderPayload.getOrderItems()!!.getMilkS()
//            lutMilkWithSugar.visibility = View.VISIBLE
//        }
//        if(orderPayload.getOrderItems()!!.getMilkNs() != ""){
//            txtMilkNSCount.text = orderPayload.getOrderItems()!!.getMilkNs()
//            lutMilkWithOutSugar.visibility = View.VISIBLE
//        }
       // txtTotaItemCount.text = getTotalBeverageCount(orderPayload.getOrderItems()!!)
        //txtDeliveryTime.text = languageStrng(orderPayload.getDelivery()!!.getDeliverySi(),orderPayload.getDelivery()!!.getDeliveryEn(),orderPayload.getDelivery()!!.getDeliveryTl(),
         //   currentLanguage)
        //txtOrderNo.text = orderId.toString()

        btnRequestOrder.setOnClickListener{
            sharedPref = this.context!!.getSharedPreferences(mcontext.getString(R.string.app_name), Context.MODE_PRIVATE)
            editor = sharedPref!!.edit()
            var apiCall = ApiCalls(mcontext)
            userSessionManager = UserSessionManager(mcontext)

            var uid = userSessionManager!!.uId
            var status = 1
            var sno = sharedPref?.getString(GlobalVariables.GATEWAY_ID,null)

            if (sno != null) {
                apiCall.updateOrderStatus(uid, sno,orderId,status, object : ResponseCallback<Boolean?> {
                    override fun onFailure(errorResponse: String?) {
                        /* Check API status and returns the error */
                        SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("API Error - Order list ")
                            .setContentText(errorResponse)
                            .show()
                    }
                    override fun onSuccess(response: Boolean?) {
                        fragListner_.onButtonClicked("updated")
                    }
                })
            }
        }

        lutClose.setOnClickListener{
            fragListner_.onButtonClicked("close")
        }

    }
    override fun onAttach(activity: Activity): Unit {
        super.onAttach(activity)
        _onAttach(activity)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        _onAttach(context)
    }
    private fun _onAttach(context: Context) {
        if (context is orderDetailOnClickListner) {
            fragListner_ = context
        } else {
            throw ClassCastException(
                context.toString()
                        + " must implemenet MyListFragment.OnItemSelectedListener"
            )
        }
    }
    interface orderDetailOnClickListner {
        fun onButtonClicked(action:String)
    }

    private fun getTotalBeverageCount(orderItems:OrderItemsInfo):String{
    var total =     orderItems.getBLackTeaS()!!.toInt() + orderItems.getBlackTeaNs()!!.toInt() +
                        orderItems.getMilkTeaS()!!.toInt() + orderItems.getMilkTeaNs()!!.toInt() +
                        orderItems.getCoffeeS()!!.toInt() + orderItems.getCoffeeNs()!!.toInt() +
                        orderItems.getMilkS()!!.toInt() + orderItems.getMilkS()!!.toInt()
        return total.toString()
    }

    private fun languageStrng(strSi: String?,strEn: String?,strTl: String?,lang: String): String? {
        var languageStrng: String?
        if (lang == "si") {
            languageStrng = strSi
        } else if (lang == "en") {
            languageStrng = strEn
        } else {
            languageStrng = strTl
        }
        return languageStrng
    }

    companion object {
        @JvmStatic
//        fun newInstance(context: Context?, orderPayload: OrderResponse?) =
//            TeaCoffeeDisplay().apply {
//                this.orderId = orderPayload!!.getId()!!.toInt()
//                this.orderPayload = orderPayload.getPayload()!!
//                this.mcontext = context!!
//            }
        fun newInstance(context: Context?) =
            TeaCoffeeDisplay().apply {
               // this.orderId = orderPayload!!.getId()!!.toInt()
               // this.orderPayload = orderPayload.getPayload()!!
                this.mcontext = context!!
            }
    }
}