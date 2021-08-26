package com.example.architecturepractice.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.architecturepractice.MainCoroutineRule
import com.example.architecturepractice.TestApplication
import com.example.architecturepractice.data.database.CalendarDatabase
import com.example.architecturepractice.data.entity.CalendarEntity
import com.example.architecturepractice.data.entity.EventEntity
import com.example.architecturepractice.data.response.calendarlist.ConferenceProperties
import com.example.architecturepractice.data.response.event.*
import com.example.architecturepractice.di.DaggerTestApplicationComponent
import com.example.architecturepractice.di.TestApplicationComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class CalendarDaoTest {

    private lateinit var database: CalendarDatabase

    private var testCalendarList: MutableList<CalendarEntity> = mutableListOf()
    private var testEventList: MutableList<EventEntity> = mutableListOf()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUpCalendarRepositoryTest() {
        val component: TestApplicationComponent = DaggerTestApplicationComponent.factory()
            .create(TestApplication())
        component.inject(this)

        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CalendarDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        database.close()
    }

    private fun insertLocalCalendarList() = runBlockingTest {

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

        database.calendarDao().insertCalendarEntity(testCalendarList)
    }

    private fun insertLocalEventList() = runBlockingTest {
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
            "calendar#event", null, organizer, null, null, 0, start3, "confirmed", "할",
            "transparent", "2021-08-08T20:44:32.223Z", "public", "naver_calendar")

        testEventList.add(event1)
        testEventList.add(event2)
        testEventList.add(event3)

        database.eventDao().insertEventEntity(testEventList)
    }

    @Test
    fun getLocalEventList_WithDate() = runBlockingTest {
        insertLocalEventList()

        val beforeCalendar = Calendar.getInstance()
        beforeCalendar.set(Calendar.DATE, 1)
        val beforeDate = beforeCalendar.run {
            time
        }

        val afterCalendar = Calendar.getInstance()
        afterCalendar.add(Calendar.MONTH, 1)
        afterCalendar.set(Calendar.DATE, 1)
        val afterDate = afterCalendar.run {
            time
        }

        val dateFormat = SimpleDateFormat("yyyy-MM", Locale.KOREA)
        val formattedBeforeDate = dateFormat.format(beforeDate)
        val formattedAfterDate = dateFormat.format(afterDate)

        val curMonthEventList = mutableListOf<EventEntity>()

        for(event in testEventList) {
            if(((event.start?.date?.substring(0, 7) ?: "9999-99" < formattedAfterDate) && (event.end?.endDate?.substring(0, 7) ?: "0000-00" >=formattedBeforeDate))
                || ((event.start?.dateTime?.substring(0, 7) ?: "9999-99" < formattedAfterDate) && (event.end?.endDateTime?.substring(0, 7) ?: "0000-00" >= formattedBeforeDate)))
            {
                curMonthEventList.add(event)
            }
        }

        val curDateEventListFromDB = database.eventDao().getEventListEntityWithDate(formattedBeforeDate, formattedAfterDate)
        assertEquals(curDateEventListFromDB, curMonthEventList)
    }

    @Test
    fun insert_And_Get_Test_LocalCalendarList() = runBlockingTest {
        insertLocalCalendarList()
        val getLocalCalendarList = database.calendarDao().getCalendarListEntity()

        assertEquals(getLocalCalendarList, testCalendarList)
    }

    @Test
    fun insert_And_Get_LocalEventList() = runBlockingTest {
        insertLocalEventList()
        val getLocalEventList = database.eventDao().getEventListEntity()

        assertEquals(getLocalEventList, testEventList)
    }

    @Test
    fun deleteAllCalendar() = runBlockingTest {
        insertLocalCalendarList()
        database.calendarDao().deleteAllCalendarEntity()

        val getLocalCalendarList = database.calendarDao().getCalendarListEntity()

        assertEquals(getLocalCalendarList, emptyList<CalendarEntity>())
    }

    @Test
    fun deleteAllEvent() = runBlockingTest {
        insertLocalEventList()
        database.eventDao().deleteAllEventEntity()

        val getLocalEventList = database.eventDao().getEventListEntity()

        assertEquals(getLocalEventList, emptyList<EventEntity>())
    }

}