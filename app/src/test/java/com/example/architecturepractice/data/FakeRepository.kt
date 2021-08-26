package com.example.architecturepractice.data

import com.example.architecturepractice.data.entity.CalendarEntity
import com.example.architecturepractice.data.entity.EventEntity
import com.example.architecturepractice.data.response.calendarlist.ConferenceProperties
import com.example.architecturepractice.data.response.event.*
import com.google.api.services.calendar.model.CalendarListEntry
import com.google.api.services.calendar.model.Events
import javax.inject.Inject

class FakeRepository : CalendarDataSource {

    private var testCalendarList: MutableList<CalendarEntity> = mutableListOf()
    private var testEventList: MutableList<EventEntity> = mutableListOf()

    private var errorState = false
    fun setReturnError(value: Boolean) {
        errorState = value
    }

    override suspend fun getCalendarList(): List<CalendarEntity>? {
        if(errorState)
            return null

        val allowedConferenceSolutionType = listOf<String>("hangoutMeet")
        val conferenceProperties = ConferenceProperties(allowedConferenceSolutionType)
        val calendar1 = CalendarEntity("ko", "#16a765", "8", conferenceProperties, null,
        "대한민국의 공휴일", "1627033255039000", "#000000", "holiday_calendar", "calendar#calendar#calendarListEntry",
        null, false, true, "대한민국의 휴일", "대한민국의 휴일", "Asia/Seoul")

        val calendar2 = CalendarEntity("ko", "#16a765", "8", conferenceProperties, null,
            "네이버 캘린더", "1627033255039002", "#000000", "naver_calendar", "calendar#calendar#calendarListEntry",
            null, false, true, "네이버 캘린더", "대한민국의 휴일", "Asia/Seoul")

        testCalendarList.add(calendar1)
        testCalendarList.add(calendar2)

        return testCalendarList
    }

    override suspend fun getEventList(calendarId: String): List<EventEntity> {

        val attendee = Attendee("hong", "hong@gmail.com", true, "yes", true)
        val attendeeList = mutableListOf<Attendee>(attendee)
        val organizer = Organizer("대한민국의 휴일", "ko.south_korea#holiday@google.com", true)
        val creator = Creator("대한민국의 휴일", "korea@korea.com", true)

        val start1 = Start("2021-07-17", null, null)
        val end1 = End("2021-07-18", null, null)
        val event1 = EventEntity(null, null, null, "2021-04-08T20:44:32.000Z", creator,
            null, end1, "12345678", "default", false, false, null,
            "https://www.google.com/calendar/event?eid=asdf", "20210717_123asd@google.com", "20210717_abs",
            "calendar#event", null, organizer, null, null, 0, start1, "confirmed", "제헌절",
            "transparent", "2021-04-08T20:44:32.223Z", "public", "holiday_calendar")

        val start2 = Start("2021-08-15", null, null)
        val end2 = End("2021-08-16", null, null)
        val event2 = EventEntity(null, null, null, "2021-04-08T20:44:32.000Z", creator,
            null, end2, "12345679", "default", false, false, null,
            "https://www.google.com/calendar/event?eid=asdf", "20210717_123asd@google.com", "20210815_abs",
            "calendar#event", null, organizer, null, null, 0, start2, "confirmed", "광복절",
            "transparent", "2021-04-08T20:44:32.223Z", "public", "holiday_calendar")

        val start3 = Start(null, "2021-09.02T07:00:00+09:00", null)
        val end3 = End(null, "2021-09.02T08:00:00+09:00", null)
        val event3 = EventEntity(attendeeList, null, null, "2021-04-08T20:44:32.000Z", creator,
            null, end3, "12345677", "default", false, false, null,
            "https://www.google.com/calendar/event?eid=asdf", "20210717_123asd@google.com", "20210902_abs",
            "calendar#event", null, organizer, null, null, 0, start3, "confirmed", "할일",
            "transparent", "2021-08-08T20:44:32.223Z", "public", "naver_calendar")

        testEventList.add(event1)
        testEventList.add(event2)
        testEventList.add(event3)
        return testEventList
    }

    override suspend fun getLocalCalendarList(): List<CalendarEntity> {
        return testCalendarList
    }

    override suspend fun getLocalEventList(): List<EventEntity> {
        return testEventList
    }

    override suspend fun getLocalEventListWithDate(beforeDate: String, afterDate: String): List<EventEntity> {
        val curMonthEventList = mutableListOf<EventEntity>()
        for(event in testEventList) {
            if(((event.start?.date?.substring(0, 7) ?: "9999-99" < afterDate) && (event.end?.endDate?.substring(0, 7) ?: "0000-00" >=beforeDate))
                || ((event.start?.dateTime?.substring(0, 7) ?: "9999-99" < afterDate) && (event.end?.endDateTime?.substring(0, 7) ?: "0000-00" >= beforeDate)))
            {
                curMonthEventList.add(event)
            }
        }
        return curMonthEventList
    }

    override suspend fun insertLocalCalendarList(calendarList: List<CalendarEntity>) {
        testCalendarList += calendarList
    }

    override suspend fun insertLocalEventList(eventList: List<EventEntity>) {
        testEventList += eventList
    }

    override suspend fun deleteAllCalendar() {
        testCalendarList.clear()
    }

    override suspend fun deleteAllEvent() {
        testEventList.clear()
    }
}