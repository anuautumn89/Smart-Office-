package com.deuron.teacoffeebackend.models.orderList

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
class OrderPayload {
    @SerializedName("delivery")
    @Expose
    private var delivery: DeliveryInfo? = null
    @SerializedName("locationId")
    @Expose
    private var locationId: String? = null
    @SerializedName("locationName")
    @Expose
    private var locationName: LocationInfo? = null
    @SerializedName("orderItems")
    @Expose
    private var orderItemsInfo: OrderItemsInfo? = null
    @SerializedName("orderRefNo")
    @Expose
    private var orderRefNo: String? = null
    @SerializedName("orderTime")
    @Expose
    private var orderTime: String? = null

    fun getDelivery(): DeliveryInfo? {
        return delivery
    }

    fun setDelivery(delivery: DeliveryInfo?) {
        this.delivery = delivery
    }

    fun getLocationId(): String? {
        return locationId
    }

    fun setLocationId(locationId: String?) {
        this.locationId = locationId
    }

    fun getLocationName(): LocationInfo? {
        return locationName
    }

    fun setLocationName(locationName: LocationInfo?) {
        this.locationName = locationName
    }

    fun getOrderItems(): OrderItemsInfo? {
        return orderItemsInfo
    }

    fun setOrderItems(orderItemsInfo: OrderItemsInfo?) {
        this.orderItemsInfo = orderItemsInfo
    }

    fun getOrderRefNo(): String? {
        return orderRefNo
    }

    fun setOrderRefNo(orderRefNo: String?) {
        this.orderRefNo = orderRefNo
    }

    fun getOrderTime(): String? {
        return orderTime
    }

    fun setOrderTime(orderTime: String?) {
        this.orderTime = orderTime
    }
}