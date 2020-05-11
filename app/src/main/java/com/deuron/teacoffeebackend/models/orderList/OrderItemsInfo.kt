package com.deuron.teacoffeebackend.models.orderList

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
class OrderItemsInfo {
    @SerializedName("bLackTeaS")
    @Expose
    private var bLackTeaS: String? = null
    @SerializedName("blackTeaNs")
    @Expose
    private var blackTeaNs: String? = null
    @SerializedName("coffeeNs")
    @Expose
    private var coffeeNs: String? = null
    @SerializedName("coffeeS")
    @Expose
    private var coffeeS: String? = null
    @SerializedName("milkNs")
    @Expose
    private var milkNs: String? = null
    @SerializedName("milkS")
    @Expose
    private var milkS: String? = null
    @SerializedName("milkTeaNs")
    @Expose
    private var milkTeaNs: String? = null
    @SerializedName("milkTeaS")
    @Expose
    private var milkTeaS: String? = null

    fun getBLackTeaS(): String? {
        return bLackTeaS
    }

    fun setBLackTeaS(bLackTeaS: String?) {
        this.bLackTeaS = bLackTeaS
    }

    fun getBlackTeaNs(): String? {
        return blackTeaNs
    }

    fun setBlackTeaNs(blackTeaNs: String?) {
        this.blackTeaNs = blackTeaNs
    }

    fun getCoffeeNs(): String? {
        return coffeeNs
    }

    fun setCoffeeNs(coffeeNs: String?) {
        this.coffeeNs = coffeeNs
    }

    fun getCoffeeS(): String? {
        return coffeeS
    }

    fun setCoffeeS(coffeeS: String?) {
        this.coffeeS = coffeeS
    }

    fun getMilkNs(): String? {
        return milkNs
    }

    fun setMilkNs(milkNs: String?) {
        this.milkNs = milkNs
    }

    fun getMilkS(): String? {
        return milkS
    }

    fun setMilkS(milkS: String?) {
        this.milkS = milkS
    }

    fun getMilkTeaNs(): String? {
        return milkTeaNs
    }

    fun setMilkTeaNs(milkTeaNs: String?) {
        this.milkTeaNs = milkTeaNs
    }

    fun getMilkTeaS(): String? {
        return milkTeaS
    }

    fun setMilkTeaS(milkTeaS: String?) {
        this.milkTeaS = milkTeaS
    }

}