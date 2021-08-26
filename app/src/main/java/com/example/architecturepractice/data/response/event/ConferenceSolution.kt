package com.example.architecturepractice.data.response.event

import androidx.room.Embedded

data class ConferenceSolution(
    val iconUri: String?,
    @Embedded val key: Key?,
    val name: String?
)