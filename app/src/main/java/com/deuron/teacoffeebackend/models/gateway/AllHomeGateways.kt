package com.deuron.teacoffeebackend.models.gateway

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AllHomeGateways(@field:SerializedName("home_gateway_serial_number")
                      @field:Expose
                      var homeGatewaySerialNumber: String?, @field:SerializedName("gateway_name")
                      @field:Expose
                      var gatewayName: String?, @field:SerializedName("gateway_status")
                      @field:Expose
                      var gatewayStatus: String?) {

    override fun toString(): String {
        return this!!.gatewayName.toString()
    }
}
