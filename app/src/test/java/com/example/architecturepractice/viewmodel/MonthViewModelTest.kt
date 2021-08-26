package com.example.architecturepractice.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.architecturepractice.LiveDataTestUtil
import com.example.architecturepractice.MainCoroutineRule
import com.example.architecturepractice.PreferenceManager
import com.example.architecturepractice.TestApplication
import com.example.architecturepractice.calendar.MonthViewModel
import com.example.architecturepractice.data.FakeRepository
import com.example.architecturepractice.di.DaggerTestApplicationComponent
import com.example.architecturepractice.di.TestApplicationComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.time.Month
import java.time.Year
import java.util.*
import javax.inject.Inject
import kotlin.properties.Delegates

@ExperimentalCoroutinesApi
class MonthViewModelTest {

    private lateinit var monthViewModel: MonthViewModel
    private val fakeRepository = FakeRepository()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val calendar = Calendar.getInstance()
    private var curYear by Delegates.notNull<Int>()
    private var curMonth by Delegates.notNull<Int>()
    private lateinit var curDate: Date
    private val curDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)

    @Before
    fun setUpMonthViewModelTest() {
        monthViewModel = MonthViewModel(fakeRepository)
        curYear = calendar.get(Calendar.YEAR)
        curMonth = calendar.get(Calendar.MONTH)
        curDate = calendar.run {
            set(Calendar.YEAR, curYear)
            set(Calendar.MONTH, curMonth)
            time
        }
    }

    @Test
    fun testInitMonthCalendar() {
        assertEquals(monthViewModel.year, curYear)
        assertEquals(monthViewModel.month, curMonth)
    }

    @Test
    fun testTransformBeforeDate() {
        val beforeCalendar = calendar
        beforeCalendar.time = curDate
        beforeCalendar.set(Calendar.DATE, 1)

        assertEquals(monthViewModel.transformBeforeDate(curDate), curDateFormat.format(beforeCalendar.time))
    }

    @Test
    fun testTransformAfterDate() {
        val afterCalendar = calendar
        afterCalendar.time = curDate
        afterCalendar.add(Calendar.MONTH, 1)
        afterCalendar.set(Calendar.DATE, 1)

        assertEquals(monthViewModel.transformAfterDate(curDate), curDateFormat.format(afterCalendar.time))
    }

    @Test
    fun testCurMonthListLiveData(): Unit = runBlockingTest {
        monthViewModel.fetchData(curDate)
        val curMonthBeforeDate = monthViewModel.transformBeforeDate(curDate)
        val curMonthAfterDate = monthViewModel.transformAfterDate(curDate)
        val curMonthEventList = monthViewModel.getCurrentMonthEvent(curMonthBeforeDate, curMonthAfterDate)
        assertEquals(LiveDataTestUtil.getValue(monthViewModel.curMonthEventListLiveData), curMonthEventList)

    }

}