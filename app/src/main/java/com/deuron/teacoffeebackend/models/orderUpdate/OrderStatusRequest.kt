package com.deuron.teacoffeebackend.models.orderUpdate
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
class OrderStatusRequest {
    @SerializedName("order_status")
    @Expose
    private var orderStatus: Int? = null

    fun getOrderStatus(): Int? {
        return orderStatus
    }

    fun setOrderStatus(orderStatus: Int?) {
        this.orderStatus = orderStatus
    }

}