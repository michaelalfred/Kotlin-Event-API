package com.example.uasmobileprogramming.model

data class Event(
    val id: Int?,
    val title: String?,
    val date: String?,
    val time: String?,
    val location: String?,
    val description: String?,
    val capacity: Int?,
    val status: String?,
    val created_at: String?,
    val updated_at: String?
    )

data class EventRequest(
    val id: String? = null,
    val title: String,
    val date: String,
    val time: String,
    val location: String,
    val description: String,
    val capacity: Int?,
    val status: String
)


data class EventResponse(
    val status: Int?,
    val message: String?,
    val data: List<Event>?,
    val timestamp: String?
    )
