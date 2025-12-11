package com.example.uasmobileprogramming.network

import com.example.uasmobileprogramming.model.EventResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("api.php")
    fun getAllEvents(): Call<EventResponse>

    @FormUrlEncoded
    @POST("api.php")
    fun createEvent(
        @Field("title") title: String,
        @Field("date") date: String,
        @Field("time") time: String,
        @Field("location") location: String,
        @Field("description") description: String?,
        @Field("capacity") capacity: Int?,
        @Field("status") status: String
    ): Call<EventResponse>

    @DELETE("api.php")
    fun deleteEvent(@Query("id") id: String): Call<EventResponse>

    @FormUrlEncoded
    @PUT("api.php")
    fun updateEvent(
        @Query("id") id: String,
        @Field("title") title: String,
        @Field("date") date: String,
        @Field("time") time: String,
        @Field("location") location: String,
        @Field("description") description: String?,
        @Field("capacity") capacity: Int?,
        @Field("status") status: String
    ): Call<EventResponse>
}