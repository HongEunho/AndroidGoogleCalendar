package com.example.architecturepractice.data

import com.example.architecturepractice.data.entity.EventEntity
import com.example.architecturepractice.data.response.calendarlist.*
import com.example.architecturepractice.data.response.event.*
import org.junit.Assert
import org.junit.Test

class EventEntityTest {

    @Test
    fun testCalendarToEntity() {
        val attendee = Attendee("hong", "hong@gmail.com", true, "accepted", true)
        val attendeeList = mutableListOf<Attendee>(attendee)

        val key = Key("test_key")
        val conferenceSolution = ConferenceSolution("https://tmp.tmp.com", key, "test_solution")
        val conferenceSolutionKey = ConferenceSolutionKey("test_solutionKey")
        val status = Status("invited")
        val createRequest = CreateRequest(conferenceSolutionKey, "test_request_id", status)
        val entryPoint = EntryPoint("phone", "meet.google.com/abc", "https://tmp.tmp.com", "123", "123", "123", "123")
        val entryPoints = mutableListOf<EntryPoint>(entryPoint)

        val conferenceData = ConferenceData("tmp_conference", conferenceSolution, createRequest, entryPoints, "signature")
        val organizer = Organizer("대한민국의 휴일", "ko.south_korea#holiday@google.com", true)
        val creator = Creator("대한민국의 휴일", "korea@korea.com", true)

        val override = Override("popup", 10)
        val overrides = mutableListOf<Override>(override)
        val reminders = Reminders(overrides, true)

        val start = Start("2021-07-17", null, null)
        val end = End("2021-07-18", null, null)
        val event = Event(attendeeList, null, conferenceData, "2021-04-08T20:44:32.000Z", creator,
            null, end, "12345678", "default", false, false, null,
            "https://www.google.com/calendar/event?eid=asdf", "20210717_123asd@google.com", "20210717_abs",
            "calendar#event", null, organizer, null, reminders, 0, start, "confirmed", "제헌절",
            "transparent", "2021-04-08T20:44:32.223Z", "public", "holiday_calendar")

        val eventEntity = EventEntity(attendeeList, null, conferenceData, "2021-04-08T20:44:32.000Z", creator,
            null, end, "12345678", "default", false, false, null,
            "https://www.google.com/calendar/event?eid=asdf", "20210717_123asd@google.com", "20210717_abs",
            "calendar#event", null, organizer, null, reminders, 0, start, "confirmed", "제헌절",
            "transparent", "2021-04-08T20:44:32.223Z", "public", "holiday_calendar")

        val eventEntityWithGetter = EventEntity(event.attendees, event.colorId, event.conferenceData, event.created, event.creator,
            event.description, event.end, event.etag, event.eventType, event.guestsCanInviteOthers, event.guestsCanSeeOtherGuests, event.hangoutLink,
            event.htmlLink, event.iCalUID, event.id,
            event.kind, event.location, event.organizer, event.recurrence, event.reminders, event.sequence, event.start, event.status, event.summary,
            event.transparency, event.updated, event.visibility, event.calendarId)

        Assert.assertEquals(eventEntity, event.toEntity())
        Assert.assertEquals(eventEntity, eventEntityWithGetter)
    }
}