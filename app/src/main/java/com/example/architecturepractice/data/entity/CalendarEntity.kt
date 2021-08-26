package com.example.architecturepractice.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.architecturepractice.data.response.calendarlist.ConferenceProperties
import com.example.architecturepractice.data.response.calendarlist.DefaultReminder
import com.example.architecturepractice.data.response.calendarlist.NotificationSettings
import com.google.api.services.calendar.model.CalendarListEntry


@Entity(tableName = "CalendarEntity")
data class CalendarEntity(
    val accessRole: String?,
    val backgroundColor: String?,
    val colorId: String?,
    @Embedded val conferenceProperties: ConferenceProperties?,
    val defaultReminders: List<DefaultReminder>?,
    val description: String?,
    val etag: String?,
    val foregroundColor: String?,
    @PrimaryKey val id: String,
    val kind: String?,
    @Embedded val notificationSettings: NotificationSettings?,
    val primary: Boolean?,
    val selected: Boolean?,
    val summary: String?,
    val summaryOverride: String?,
    val timeZone: String?
)