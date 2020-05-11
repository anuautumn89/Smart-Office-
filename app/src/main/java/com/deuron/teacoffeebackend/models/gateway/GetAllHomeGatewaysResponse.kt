package com.deuron.teacoffeebackend.models.gateway

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetAllHomeGatewaysResponse {
    @SerializedName("status")
    @Expose
    var status: Int? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("response")
    @Expose
    var response: List<AllHomeGateways>? = null
}
