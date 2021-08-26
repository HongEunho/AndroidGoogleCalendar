package com.example.architecturepractice.data.response.event

data class Attendee(
    val displayName: String?,
    val email: String?,
    val organizer: Boolean?,
    val responseStatus: String?,
    val self: Boolean?
)