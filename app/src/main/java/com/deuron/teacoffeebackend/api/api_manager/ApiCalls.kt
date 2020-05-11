package com.deuron.teacoffeebackend.api.api_manager

import android.content.Context
import android.os.Handler
import com.deuron.teacoffeebackend.GlobalVariables.Companion.OrderResponseArrList
import com.deuron.teacoffeebackend.api.RestAPIClient
import com.deuron.teacoffeebackend.api.interfaces.RESTAPIService
import com.deuron.teacoffeebackend.interfaces.ApiCallInterface
import com.deuron.teacoffeebackend.models.orderUpdate.OrderStatusRequest
import com.deuron.teacoffeebackend.models.orderUpdate.OrderUpdateResponse
import com.deuron.teacoffeebackend.models.orderList.OrderListResponse
import com.deuron.teacoffeebackend.models.orderList.OrderResponse
import com.deuron.teacoffeebackend.wifi_checker.WifiChecker
import com.dpitl.deuron.meetingroomt3g.models.LoginRequest
import com.dpitl.deuron.meetingroomt3g.models.LoginResponse
import com.dpitl.deuron.meetingroomt3g.my_classes.text_to_speech.user_sessions.UserSessionManager

class  ApiCalls(context: Context) : ApiCallInterface{
    private var apiCalls: ApiCalls? = null
    internal var mcontext: Context = context
    private var wifiChecker: WifiChecker? = null
    private var isWifi: Boolean? = null
    private var userSessionManager: UserSessionManager? = null
    override fun userLogin(
        userName: String,
        password: String?,
        response: ResponseCallback<String?>?
    ) {
        wifiChecker = WifiChecker()
        isWifi = wifiChecker!!.checkingWifiState(this.mcontext)
        if (isWifi!!) {
            //userSessionManager = UserSessionManager(Context)
            val restapiService = RestAPIClient.demoClient!!.create(
                RESTAPIService::class.java)
            Handler().postDelayed({
                val request = LoginRequest()
                request.user_name = userName
                request.user_password = password
                restapiService.login(request).enqueue(object : retrofit2.Callback<LoginResponse> {
                    override fun onResponse(call: retrofit2.Call<LoginResponse>, apiResponse: retrofit2.Response<LoginResponse>) {
                        if (apiResponse.body() != null && apiResponse.body()!!.message == "user") {
                            userSessionManager = UserSessionManager(mcontext)
                            userSessionManager!!.isUserLogin = true
                            userSessionManager!!.uId = Integer.parseInt(apiResponse.body()!!.response!!.user_id!!)
                        }
                        response!!.onSuccess("true")
                    }
                    override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {
                        response!!.onFailure(t.message)
                    }
                })
            }, 1000)
        } else {
            response!!.onFailure("No Wifi")
        }
    }

    override fun getOrderList(userId: Int, response: ResponseCallback<String?>?) {
        wifiChecker = WifiChecker()
        isWifi = wifiChecker!!.checkingWifiState(this.mcontext)
        if (isWifi!!) {
            val restapiService = RestAPIClient.demoClient!!.create(
                RESTAPIService::class.java)
                restapiService.getOrders(userId).enqueue(object : retrofit2.Callback<OrderListResponse> {
                    override fun onResponse(call: retrofit2.Call<OrderListResponse>, apiResponse: retrofit2.Response<OrderListResponse>) {
                        if (apiResponse.body() != null && apiResponse.body()!!.message == "order list") {
                            if (apiResponse.body()!!.response != null){
                                for (i in apiResponse.body()!!.response!!.indices){
                                    if(apiResponse.body()!!.response?.get(i)?.getOrderStatus() == "0"){
                                        var orderResponse = OrderResponse()
                                        orderResponse = apiResponse!!.body()!!.response!!.get(i)
                                        if(OrderResponseArrList!!.isEmpty()) {
                                            OrderResponseArrList!!.add(orderResponse)
                                        }
                                    }
                                }
                                response!!.onSuccess("true")
                            }
                        }
                    }
                    override fun onFailure(call: retrofit2.Call<OrderListResponse>, t: Throwable) {
                        response!!.onFailure(t.message)
                    }
                })
        } else {
            response!!.onFailure("No Wifi")
        }
    }

    override fun updateOrderStatus(
        userId:Int,
        sno:String,
        orderId:Int,
        status:Int, response: ResponseCallback<Boolean?>?) {
        wifiChecker = WifiChecker()
        isWifi = wifiChecker!!.checkingWifiState(this.mcontext)
        if (isWifi!!) {
            val request =
                OrderStatusRequest()
            request.setOrderStatus(1)
            val restapiService = RestAPIClient.demoClient!!.create(
                RESTAPIService::class.java)
            restapiService.updateOrderStatus(request,userId,sno,orderId).enqueue(object : retrofit2.Callback<OrderUpdateResponse> {
                override fun onResponse(call: retrofit2.Call<OrderUpdateResponse>, apiResponse: retrofit2.Response<OrderUpdateResponse>) {
                    if (apiResponse.body() != null && apiResponse.body()!!.message == "successfully updated") {
                        if (apiResponse.body()!!.response != null){
                            if(!OrderResponseArrList!!.isEmpty()) {
                                for(item in OrderResponseArrList!!){
                                    if(item.getId() == apiResponse.body()!!.response!!.getId()){
                                        var index = OrderResponseArrList!!.indexOf(item);
                                        OrderResponseArrList!!.removeAt(index)
                                    }

                                }
                            }
                            response!!.onSuccess(true)
                        }
                    }
                }
                override fun onFailure(call: retrofit2.Call<OrderUpdateResponse>, t: Throwable) {
                    response!!.onFailure(t.message)
                }
            })
        } else {
            response!!.onFailure("No Wifi")
        }
    }


}


