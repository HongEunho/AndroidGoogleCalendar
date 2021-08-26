package com.example.architecturepractice.data.response.calendarlist

import com.google.gson.annotations.SerializedName

data class CalendarListResponse(
    @SerializedName("etag") val etag: String,
    @SerializedName("item") val calendars: List<Calendar>,
    @SerializedName("kind") val kind: String,
    @SerializedName("nextSyncToken") val nextSyncToken: String
)