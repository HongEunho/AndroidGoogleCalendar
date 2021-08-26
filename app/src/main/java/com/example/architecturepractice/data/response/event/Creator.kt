package com.example.architecturepractice.data.response.event

import com.google.gson.annotations.SerializedName

data class Creator(
    @SerializedName("displayName") val creatorDisplayName: String?,
    @SerializedName("email") val creatorEmail: String?,
    @SerializedName("self") val creatorSelf: Boolean?
)