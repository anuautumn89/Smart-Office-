package com.deuron.teacoffeebackend.api.interfaces


import com.deuron.teacoffeebackend.GlobalVariables
import com.deuron.teacoffeebackend.models.orderUpdate.OrderStatusRequest
import com.deuron.teacoffeebackend.models.orderUpdate.OrderUpdateResponse
import com.deuron.teacoffeebackend.models.gateway.GetAllHomeGatewaysResponse
import com.deuron.teacoffeebackend.models.orderList.OrderListResponse
import com.dpitl.deuron.meetingroomt3g.models.*

import retrofit2.Call
import retrofit2.http.*

interface RESTAPIService {
    /**
     * This is the REST API Service to work with Retrofit.
     */

    //Login
    @Headers("api_key: " + GlobalVariables.API_KEY)
    @POST("authentication/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

//    //Get All Home gateways
    //deuron_automation_api_cloud/v1.0/api/orders/index/uid/6
    @Headers("api_key: " + GlobalVariables.API_KEY)
    @GET("orders/index/uid/{uid}")
    fun getOrders(@Path("uid") uid: Int): Call<OrderListResponse>

    //Update Order Status
    //<service url> /api/orders/status/uid/{{uid}}/sno/{...}/id/{...}
    @Headers("api_key: " + GlobalVariables.API_KEY)
    @PUT("orders/status/uid/{uid}/sno/{sno}/id/{id}")
    fun updateOrderStatus(@Body request: OrderStatusRequest, @Path("uid") uid: Int, @Path("sno") sno: String?, @Path("id") id: Int): Call<OrderUpdateResponse>

    //Get All Home gateways
    @Headers("api_key: " + GlobalVariables.API_KEY)
    @GET("home_gateway/index/uid/{uid}")
    fun getAllHomeGateways(@Path("uid") uid: Int): Call<GetAllHomeGatewaysResponse>
}
