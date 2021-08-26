package com.example.architecturepractice.calendar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.architecturepractice.data.CalendarDataSource
import com.example.architecturepractice.data.entity.EventEntity
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MonthViewModel @Inject constructor(private val calendarRepository: CalendarDataSource): ViewModel() {

    var _curMonthEventListLiveData = MutableLiveData<List<EventEntity>>()
    var curMonthEventListLiveData : LiveData<List<EventEntity>> = _curMonthEventListLiveData

    var year = 0
    var month = 0

    init {
        val calendar= Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
    }

    fun fetchData(date: Date): Job = viewModelScope.launch {
        val beforeDate = transformBeforeDate(date)
        val afterDate = transformAfterDate(date)
        val curMonthEventList = getCurrentMonthEvent(beforeDate, afterDate)
        setCurMonthEventList(curMonthEventList)
    }

    fun transformBeforeDate(curDate: Date): String {
        val beforeCalendar = Calendar.getInstance()
        beforeCalendar.time = curDate
        beforeCalendar.set(Calendar.DATE, 1)

        return formatDate(beforeCalendar.time)
    }

    fun transformAfterDate(curDate: Date): String {
        val afterCalendar = Calendar.getInstance()
        afterCalendar.time = curDate
        afterCalendar.add(Calendar.MONTH, 1)
        afterCalendar.set(Calendar.DATE, 1)

        return formatDate(afterCalendar.time)
    }

    fun formatDate(date: Date): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        return dateFormat.format(date)
    }

    suspend fun getCurrentMonthEvent(beforeDate: String, afterDate: String): List<EventEntity> {
        return calendarRepository.getLocalEventListWithDate(beforeDate, afterDate)
    }

    private fun setCurMonthEventList(list: List<EventEntity>) {
        _curMonthEventListLiveData.postValue(list)
    }

}