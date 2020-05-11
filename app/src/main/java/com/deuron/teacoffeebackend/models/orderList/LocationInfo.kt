package com.deuron.teacoffeebackend.models.orderList

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class LocationInfo {
    @SerializedName("locationNameEn")
    @Expose
    private var locationNameEn: String? = null
    @SerializedName("locationNameSi")
    @Expose
    private var locationNameSi: String? = null
    @SerializedName("locationNameTl")
    @Expose
    private var locationNameTl: String? = null

    fun getLocationNameEn(): String? {
        return locationNameEn
    }

    fun setLocationNameEn(locationNameEn: String?) {
        this.locationNameEn = locationNameEn
    }

    fun getLocationNameSi(): String? {
        return locationNameSi
    }

    fun setLocationNameSi(locationNameSi: String?) {
        this.locationNameSi = locationNameSi
    }

    fun getLocationNameTl(): String? {
        return locationNameTl
    }

    fun setLocationNameTl(locationNameTl: String?) {
        this.locationNameTl = locationNameTl
    }
}