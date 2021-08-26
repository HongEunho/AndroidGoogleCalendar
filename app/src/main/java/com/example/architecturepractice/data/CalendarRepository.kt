package com.example.architecturepractice.data

import com.example.architecturepractice.PreferenceManager
import com.example.architecturepractice.data.database.CalendarDatabase
import com.example.architecturepractice.data.entity.CalendarEntity
import com.example.architecturepractice.data.entity.EventEntity
import com.example.architecturepractice.data.network.ApiProvider
import com.example.architecturepractice.data.response.calendarlist.Calendar
import com.example.architecturepractice.data.response.event.Event
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.services.calendar.model.CalendarList
import com.google.api.services.calendar.model.Events
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

class CalendarRepository @Inject constructor(
    private val ioDispatchers: CoroutineDispatcher = Dispatchers.IO,
    private val dataBase: CalendarDatabase
) : CalendarDataSource {

    @Inject
    lateinit var preferenceManager: PreferenceManager

    private val service = ApiProvider.apiService

    @Throws(UserRecoverableAuthIOException::class)
    override suspend fun getCalendarList(): List<CalendarEntity>? = withContext(ioDispatchers) {
        var pageToken: String? = null
        var syncToken: String? = preferenceManager.getCalendarNextSyncToken()

        val saveCalendarList = mutableListOf<CalendarEntity>()

        do {
            val calendarList: CalendarList = try {
                service.calendarList().list().setPageToken(pageToken).setSyncToken(syncToken).execute()
            } catch (exception: UserRecoverableAuthIOException) {
                throw exception
            } catch (exception: GoogleJsonResponseException) {
                service.calendarList().list().setPageToken(pageToken).execute()
            }

            val items = calendarList.items
            if (items != null) {
                for (calendarListEntry in items) {
                    val data = Gson().fromJson(calendarListEntry.toString(), Calendar::class.java)
                    saveCalendarList.add(data.toEntity())
                }
            }
            pageToken = calendarList.nextPageToken
            syncToken = calendarList.nextSyncToken
        } while (pageToken != null)

        if (syncToken != null) {
            preferenceManager.putCalendarNextSyncToken(syncToken)
        }

        return@withContext saveCalendarList
    }

    override suspend fun getLocalCalendarList(): List<CalendarEntity> =
        withContext(ioDispatchers) {
            return@withContext dataBase.calendarDao().getCalendarListEntity()
        }

    override suspend fun getLocalEventList(): List<EventEntity> = withContext(ioDispatchers) {
        return@withContext dataBase.eventDao().getEventListEntity()
    }

    override suspend fun deleteAllCalendar() = withContext(ioDispatchers) {
        dataBase.calendarDao().deleteAllCalendarEntity()
    }

    override suspend fun deleteAllEvent() = withContext(ioDispatchers) {
        dataBase.eventDao().deleteAllEventEntity()
    }

    override suspend fun getEventList(calendarId: String): List<EventEntity> =
        withContext(ioDispatchers) {
            val syncTokenJson = preferenceManager.getEventNextSyncToken()
            var syncToken: String? = null
            var syncTokenMap= HashMap<String, String>()

            if (syncTokenJson != null) {
                val type = object : TypeToken<HashMap<String, String>>(){}.type
                syncTokenMap = Gson().fromJson(syncTokenJson, type)
                syncToken = syncTokenMap[calendarId]
            }

            var pageToken: String? = null

            val saveCalendarEventList = mutableListOf<EventEntity>()
            do {
                val events: Events = try {
                    service.events().list(calendarId).setPageToken(pageToken).setSyncToken(syncToken).execute()
                } catch (exception: GoogleJsonResponseException) {
                    service.events().list(calendarId).setPageToken(pageToken).execute()
                }

                val items = events.items
                for (event in items) {
                    val data = Gson().fromJson(event.toString(), Event::class.java)
                    data.calendarId = calendarId
                    saveCalendarEventList.add(data.toEntity())
                }
                pageToken = events.nextPageToken
                syncToken = events.nextSyncToken
            } while (pageToken != null)

            if (syncToken != null) {
                syncTokenMap[calendarId] = syncToken
                preferenceManager.putEventNextSyncToken(Gson().toJson(syncTokenMap))
            }

            return@withContext saveCalendarEventList
        }


    override suspend fun insertLocalCalendarList(calendarList: List<CalendarEntity>) =
        withContext(ioDispatchers) {
            dataBase.calendarDao().insertCalendarEntity(calendarList)
        }

    override suspend fun insertLocalEventList(eventList: List<EventEntity>) =
        withContext(ioDispatchers) {
            dataBase.eventDao().insertEventEntity(eventList)
        }

    override suspend fun getLocalEventListWithDate(beforeDate: String, afterDate: String): List<EventEntity> =
        withContext(ioDispatchers) {
            dataBase.eventDao().getEventListEntityWithDate(beforeDate, afterDate)
        }

}