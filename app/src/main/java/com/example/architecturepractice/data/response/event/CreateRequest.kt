package com.example.architecturepractice.data.response.event

import androidx.room.Embedded

data class CreateRequest(
    @Embedded val conferenceSolutionKey: ConferenceSolutionKey?,
    val requestId: String?,
    @Embedded val status: Status?
)