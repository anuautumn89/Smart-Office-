package com.deuron.teacoffeebackend.models.orderList
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
class OrderResponse {
    @SerializedName("id")
    @Expose
    private var id: String? = null
    @SerializedName("sno")
    @Expose
    private var sno: String? = null
    @SerializedName("gateway_name")
    @Expose
    private var gatewayName: String? = null
    @SerializedName("default_exp_time")
    @Expose
    private var defaultExpTime: String? = null
    @SerializedName("order_time")
    @Expose
    private var orderTime: String? = null
    @SerializedName("request_time")
    @Expose
    private var requestTime: String? = null
    @SerializedName("expire_time")
    @Expose
    private var expireTime: String? = null
    @SerializedName("order_status")
    @Expose
    private var orderStatus: String? = null
    @SerializedName("accepted_time")
    @Expose
    private var acceptedTime: String? = null
    @SerializedName("requested_device_serial")
    @Expose
    private var requestedDeviceSerial: String? = null
    @SerializedName("destination_device_serial")
    @Expose
    private var destinationDeviceSerial: String? = null
    @SerializedName("payload")
    @Expose
    private var payload: OrderPayload? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getSno(): String? {
        return sno
    }

    fun setSno(sno: String?) {
        this.sno = sno
    }

    fun getGatewayName(): String? {
        return gatewayName
    }

    fun setGatewayName(gatewayName: String?) {
        this.gatewayName = gatewayName
    }

    fun getDefaultExpTime(): String? {
        return defaultExpTime
    }

    fun setDefaultExpTime(defaultExpTime: String?) {
        this.defaultExpTime = defaultExpTime
    }

    fun getOrderTime(): String? {
        return orderTime
    }

    fun setOrderTime(orderTime: String?) {
        this.orderTime = orderTime
    }

    fun getRequestTime(): String? {
        return requestTime
    }

    fun setRequestTime(requestTime: String?) {
        this.requestTime = requestTime
    }

    fun getExpireTime(): String? {
        return expireTime
    }

    fun setExpireTime(expireTime: String?) {
        this.expireTime = expireTime
    }

    fun getOrderStatus(): String? {
        return orderStatus
    }

    fun setOrderStatus(orderStatus: String?) {
        this.orderStatus = orderStatus
    }

    fun getAcceptedTime(): String? {
        return acceptedTime
    }

    fun setAcceptedTime(acceptedTime: String?) {
        this.acceptedTime = acceptedTime
    }

    fun getRequestedDeviceSerial(): String? {
        return requestedDeviceSerial
    }

    fun setRequestedDeviceSerial(requestedDeviceSerial: String?) {
        this.requestedDeviceSerial = requestedDeviceSerial
    }

    fun getDestinationDeviceSerial(): String? {
        return destinationDeviceSerial
    }

    fun setDestinationDeviceSerial(destinationDeviceSerial: String?) {
        this.destinationDeviceSerial = destinationDeviceSerial
    }

    fun getPayload(): OrderPayload? {
        return payload
    }

    fun setPayload(payload: OrderPayload?) {
        this.payload = payload
    }

}