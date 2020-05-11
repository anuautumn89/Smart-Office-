package com.deuron.teacoffeebackend.interfaces

import com.deuron.teacoffeebackend.api.api_manager.ResponseCallback
import com.deuron.teacoffeebackend.models.orderList.OrderResponse

interface ApiCallInterface{
    fun userLogin(userName:String , password: String?, response: ResponseCallback<String?>?)
    //fun getOrderList(userId:Int,response: ResponseCallback<T>)
    fun getOrderList(userId:Int,response: ResponseCallback<String?>?)
    fun updateOrderStatus(userId:Int,sno:String,orderId:Int,orderStatus:Int,response:ResponseCallback<Boolean?>?)}