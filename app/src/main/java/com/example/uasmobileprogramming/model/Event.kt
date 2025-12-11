package com.example.uasmobileprogramming.model

data class Event(
    val id: String?,
    val title: String?,
    val date: String?,
    val time: String?,
    val location: String?,
    val description: String?,
    val capacity: String?,
    val status: String?,
    val created_at: String?,
    val updated_at: String?
    )

data class EventResponse(
    val status: Int?,
    val message: String?,
    val data: List<Event>?,
    val timestamp: String?
    )
