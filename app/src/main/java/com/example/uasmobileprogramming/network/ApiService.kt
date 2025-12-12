package com.example.uasmobileprogramming.network

import com.example.uasmobileprogramming.model.EventRequest
import com.example.uasmobileprogramming.model.EventResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("api.php")
    fun getAllEvents(): Call<EventResponse>

    @POST("api.php")
    fun createEvent(@Body request: EventRequest): Call<ResponseBody>

    @PUT("api.php")
    fun updateEvent(@Query("id") id: String, @Body request: EventRequest): Call<ResponseBody>

    @DELETE("api.php")
    fun deleteEvent(@Query("id") id: String): Call<ResponseBody>
}
