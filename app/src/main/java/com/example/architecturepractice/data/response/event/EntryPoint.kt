package com.example.architecturepractice.data.response.event

data class EntryPoint(
    val entryPointType: String?,
    val label: String?,
    val uri: String?,
    val meetingCode: String?,
    val passCode: String?,
    val password: String?,
    val pin: String?
)