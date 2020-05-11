package com.deuron.teacoffeebackend.models.orderUpdate

import com.deuron.teacoffeebackend.models.orderList.OrderResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
class OrderUpdateResponse {
    @SerializedName("status")
    @Expose
    var status: Int? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("response")
    @Expose
    var response: OrderResponse? = null

}