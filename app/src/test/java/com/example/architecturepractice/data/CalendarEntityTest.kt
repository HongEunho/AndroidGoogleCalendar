package com.example.architecturepractice.data

import com.example.architecturepractice.data.entity.CalendarEntity
import com.example.architecturepractice.data.response.calendarlist.*
import org.junit.Assert.assertEquals
import org.junit.Test

class CalendarEntityTest {

    @Test
    fun testCalendarToEntity() {
        val defaultReminder = DefaultReminder("popup", 10)
        val notification = Notification("email", "eventChange")
        val notifications = mutableListOf<Notification>(notification)

        val defaultRemindes = listOf<DefaultReminder>(defaultReminder)
        val notificationSettings = NotificationSettings(notifications)
        val allowedConferenceSolutionType = listOf<String>("hangoutMeet")
        val conferenceProperties = ConferenceProperties(allowedConferenceSolutionType)

        val calendar = Calendar("ko", "#16a765", "8", conferenceProperties, defaultRemindes,
            "대한민국의 공휴일", "1627033255039000", "#000000", "holiday_calendar", "calendar#calendar#calendarListEntry",
            notificationSettings, false, true, "대한민국의 휴일", "대한민국의 휴일", "Asia/Seoul")

        val calendarEntity = CalendarEntity("ko", "#16a765", "8", conferenceProperties, defaultRemindes,
            "대한민국의 공휴일", "1627033255039000", "#000000", "holiday_calendar", "calendar#calendar#calendarListEntry",
            notificationSettings, false, true, "대한민국의 휴일", "대한민국의 휴일", "Asia/Seoul")

        val calendarEntityWithGetter = CalendarEntity(calendar.accessRole, calendar.backgroundColor, calendar.colorId, calendar.conferenceProperties, calendar.defaultReminders,
            calendar.description, calendar.etag, calendar.foregroundColor, calendar.id, calendar.kind,
            calendar.notificationSettings, calendar.primary, calendar.selected, calendar.summary, calendar.summaryOverride, calendar.timeZone)

        assertEquals(calendarEntity, calendar.toEntity())
        assertEquals(calendarEntity, calendarEntityWithGetter)
    }
}