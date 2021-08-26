package com.example.architecturepractice.data.response.event

import androidx.room.Embedded

data class ConferenceData(
    val conferenceId: String?,
    @Embedded val conferenceSolution: ConferenceSolution?,
    @Embedded val createRequest: CreateRequest?,
    val entryPoints: List<EntryPoint>?,
    val signature: String?
)