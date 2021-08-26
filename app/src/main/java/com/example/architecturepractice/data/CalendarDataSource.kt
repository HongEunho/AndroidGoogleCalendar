package com.example.architecturepractice.data

import com.example.architecturepractice.data.entity.CalendarEntity
import com.example.architecturepractice.data.entity.EventEntity
import com.example.architecturepractice.data.response.calendarlist.Calendar
import com.example.architecturepractice.data.response.calendarlist.CalendarListResponse
import com.google.api.services.calendar.model.CalendarListEntry
import com.google.api.services.calendar.model.Event
import com.google.api.services.calendar.model.Events

interface CalendarDataSource {

    suspend fun getCalendarList(): List<CalendarEntity>?

    suspend fun getLocalCalendarList(): List<CalendarEntity>

    suspend fun getEventList(calendarId: String): List<EventEntity>

    suspend fun getLocalEventListWithDate(beforeDate: String, afterDate: String): List<EventEntity>

    suspend fun insertLocalCalendarList(calendarList: List<CalendarEntity>)

    suspend fun insertLocalEventList(eventList: List<EventEntity>)

    suspend fun getLocalEventList(): List<EventEntity>

    suspend fun deleteAllCalendar()

    suspend fun deleteAllEvent()
}