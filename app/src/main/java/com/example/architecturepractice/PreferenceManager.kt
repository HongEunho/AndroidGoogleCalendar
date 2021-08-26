package com.example.architecturepractice

import android.content.Context
import android.content.SharedPreferences
import com.google.api.services.calendar.Calendar

class PreferenceManager(
    private val context: Context
) {

    companion object {
        const val PREFERENCES_NAME = "architecturePractice"
        private const val DEFAULT_VALUE_STRING = ""
        private const val DEFAULT_VALUE_BOOLEAN = false
        private const val DEFAULT_VALUE_INT = -1
        private const val DEFAULT_VALUE_LONG = -1L
        private const val DEFAULT_VALUE_FLOAT = -1f

        const val KEY_ID_TOKEN = "ID_TOKEN"
        const val KEY_CALENDAR_NEXTPAGE_TOKEN = "CALENDAR_NEXTPAGE_TOKEN"
        const val KEY_CALENDAR_NEXTSYNC_TOKEN = "CALENDAR_NEXTSYNC_TOKEN"
        const val KEY_EVENT_NEXTPAGE_TOKEN = "EVENT_NEXTPAGE_TOKEN"
        const val KEY_EVENT_NEXTSYNC_TOKEN = "EVENT_NEXTSYNC_TOKEN"
    }

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    private val prefs by lazy { getPreferences(context) }

    private val editor by lazy { prefs.edit() }


    fun setString(key: String?, value: String?) {
        editor.putString(key, value)
        editor.apply()
    }

    fun setBoolean(key: String?, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }


    fun setInt(key: String?, value: Int) {
        editor.putInt(key, value)
        editor.apply()
    }


    fun setLong(key: String?, value: Long) {
        editor.putLong(key, value)
        editor.apply()
    }


    fun setFloat(key: String?, value: Float) {
        editor.putFloat(key, value)
        editor.apply()
    }


    fun getString(key: String?): String? {
        return prefs.getString(key, DEFAULT_VALUE_STRING)
    }


    fun getBoolean(key: String?): Boolean {
        return prefs.getBoolean(key, DEFAULT_VALUE_BOOLEAN)
    }


    fun getInt(key: String?): Int {
        return prefs.getInt(key, DEFAULT_VALUE_INT)
    }


    fun getLong(key: String?): Long {
        return prefs.getLong(key, DEFAULT_VALUE_LONG)
    }


    fun getFloat(key: String?): Float {
        return prefs.getFloat(key, DEFAULT_VALUE_FLOAT)
    }

    fun clear() {
        editor.clear()
        editor.apply()
    }

    fun putIdToken(idToken: String) {
        editor.putString(KEY_ID_TOKEN, idToken)
        editor.apply()
    }

    fun getIdToken(): String? {
        return prefs.getString(KEY_ID_TOKEN, null)
    }

    fun removedIdToken() {
        editor.putString(KEY_ID_TOKEN, null)
        editor.apply()
    }

    fun putCalendarNextSyncToken(syncToken: String) {
        editor.putString(KEY_CALENDAR_NEXTSYNC_TOKEN, syncToken)
        editor.apply()
    }

    fun getCalendarNextSyncToken(): String? {
        return prefs.getString(KEY_CALENDAR_NEXTSYNC_TOKEN, null)
    }

    fun removeCalendarNextSyncToken() {
        editor.putString(KEY_CALENDAR_NEXTSYNC_TOKEN, null)
        editor.apply()
    }

    fun putEventNextSyncToken(syncToken: String) {
        editor.putString(KEY_EVENT_NEXTSYNC_TOKEN, syncToken)
        editor.apply()
    }

    fun getEventNextSyncToken(): String? {
        return prefs.getString(KEY_EVENT_NEXTSYNC_TOKEN, null)
    }

    fun removeEventNextSyncToken() {
        editor.putString(KEY_EVENT_NEXTSYNC_TOKEN, null)
        editor.apply()
    }
}