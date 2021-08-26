package com.example.architecturepractice.data.response.event

import com.google.gson.annotations.SerializedName

data class ConferenceSolutionKey(
    @SerializedName("type") val solutionKeyType: String?
)