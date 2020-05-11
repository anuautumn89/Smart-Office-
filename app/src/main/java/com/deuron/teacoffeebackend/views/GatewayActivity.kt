package com.deuron.teacoffeebackend.views

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import cn.pedant.SweetAlert.SweetAlertDialog
import com.deuron.teacoffeebackend.GlobalVariables
import com.deuron.teacoffeebackend.R
import com.deuron.teacoffeebackend.api.RestAPIClient
import com.deuron.teacoffeebackend.api.interfaces.RESTAPIService
import com.deuron.teacoffeebackend.models.gateway.AllHomeGateways
import com.deuron.teacoffeebackend.models.gateway.GetAllHomeGatewaysResponse
import com.deuron.teacoffeebackend.views.MainActivity
import com.dpitl.deuron.meetingroomt3g.my_classes.text_to_speech.user_sessions.UserSessionManager

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class GatewayActivity : AppCompatActivity() {
    internal var context: Context = this
    internal var gv = GlobalVariables(context)
    internal var sharedPref: SharedPreferences? = null
    internal var editor: SharedPreferences.Editor? = null
    private var dialog: ProgressDialog? = null
    private var userSessionManager: UserSessionManager? = null
    //private var errorMessage: ErrorMessage? = null
    private var spinner: Spinner? = null
    private var uId: Int = 0
    private var userName = ""

    private lateinit var pDialog: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gateway_)
        sharedPref = this.context.getSharedPreferences(this.context.getString(R.string.app_name), Context.MODE_PRIVATE)
        userSessionManager = UserSessionManager(context)
        uId = userSessionManager!!.uId
        editor = sharedPref!!.edit()
        dialog = ProgressDialog(context as Activity)
        //uId = Integer.parseInt(intent.getStringExtra("U_ID"))
        //errorMessage = ErrorMessage(context)
        spinner = findViewById(R.id.gateway_spinner)
        bindGatewayData()
    }

    fun getSelectedGateway(v: View) {
        try {
            val gateway = spinner!!.selectedItem as AllHomeGateways
            GlobalVariables.gatewayId = gateway.homeGatewaySerialNumber
            //TODO Add gateway serial to shared preferences
            editor!!.putString(GlobalVariables.GATEWAY_ID, gateway.homeGatewaySerialNumber)
            editor!!.putString(GlobalVariables.GATEWAY_NAME_SI, gateway.gatewayName)
            editor!!.putString(GlobalVariables.GATEWAY_NAME_TL, gateway.gatewayName)
            editor!!.putString(GlobalVariables.GATEWAY_NAME_EN, gateway.gatewayName)
            //TODO - order exp time setup screen
            editor!!.putString(GlobalVariables.ORDER_EXPIRE_TIME,"120")
            editor!!.commit()
            pDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
            pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
            pDialog.titleText = "please wait.."
            pDialog.setCancelable(true)
            pDialog.show()
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("USER_NAME", userName)
            intent.putExtra("OPENING_ACTIVITY", true)
            context.startActivity(intent)
            pDialog.dismiss()
        } catch (e: Exception) {
            pDialog.dismiss()
            SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("System Error")
                .setContentText("Error Occurred while selecting gateway item")
                .show()
        }

    }

    private fun bindGatewayData() {
        try {
            val restapiService = RestAPIClient!!.demoClient!!.create<RESTAPIService>(RESTAPIService::class.java!!)
            restapiService.getAllHomeGateways(uId).enqueue(object : Callback<GetAllHomeGatewaysResponse> {
                override fun onResponse(call: Call<GetAllHomeGatewaysResponse>, apiResponse: Response<GetAllHomeGatewaysResponse>) {
                    if (apiResponse.body() != null && apiResponse.body()!!.message == "home gateway list") {
                        val gatewayList = ArrayList<AllHomeGateways>()
                        for (temp in apiResponse.body()!!.response!!) {
                            gatewayList.add(temp)
                        }
                        val adapter: ArrayAdapter<AllHomeGateways>
                        adapter = ArrayAdapter(context, R.layout.custom_spinner, gatewayList)
                        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown)
                        spinner!!.adapter = adapter
                    } else {
                        //TODO
                        /* Check API status and return the error */
                        SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("API Failure")
                            .setContentText("Error Occurred while obtaining gateway item")
                            .show()
                    }
                }
                override fun onFailure(call: Call<GetAllHomeGatewaysResponse>, t: Throwable) {
                    SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Error")
                        .setContentText(t.message)
                        .show()
                }
            })
        } catch (e: Exception) {
            SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Error")
                .setContentText(e.message)
                .show()
        }
    }

    fun bnGatewayCancel(view: View) {
        super.onBackPressed()
    }
}
