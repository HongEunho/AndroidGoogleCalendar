package com.example.architecturepractice.data.network

import android.accounts.Account
import android.content.Context
import android.util.Log
import com.example.architecturepractice.BuildConfig
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object ApiProvider {

    lateinit var apiService: Calendar

    fun createService(transport: HttpTransport, jsonFactory: JsonFactory, credential: GoogleAccountCredential) {
        apiService = Calendar.Builder(transport, jsonFactory, credential)
            .setApplicationName("BeforeArchitecture QuickStart")
            .build()
    }

}