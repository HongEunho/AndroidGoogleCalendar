package com.example.architecturepractice.data.response.event

import com.google.gson.annotations.SerializedName

data class EventListResponse(
    val accessRole: String?,
    val defaultReminders: List<DefaultReminder>?,
    val etag: String?,
    @SerializedName("items") val events: List<Event>?,
    val kind: String?,
    val nextSyncToken: String?,
    val summary: String?,
    val timeZone: String?,
    val updated: String?
)