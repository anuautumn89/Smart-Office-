package com.deuron.teacoffeebackend.models.orderList

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
class DeliveryInfo {
    @SerializedName("deliveryEn")
    @Expose
    private var deliveryEn: String? = null
    @SerializedName("deliverySi")
    @Expose
    private var deliverySi: String? = null
    @SerializedName("deliveryTl")
    @Expose
    private var deliveryTl: String? = null

    fun getDeliveryEn(): String? {
        return deliveryEn
    }

    fun setDeliveryEn(deliveryEn: String?) {
        this.deliveryEn = deliveryEn
    }

    fun getDeliverySi(): String? {
        return deliverySi
    }

    fun setDeliverySi(deliverySi: String?) {
        this.deliverySi = deliverySi
    }

    fun getDeliveryTl(): String? {
        return deliveryTl
    }

    fun setDeliveryTl(deliveryTl: String?) {
        this.deliveryTl = deliveryTl
    }
}