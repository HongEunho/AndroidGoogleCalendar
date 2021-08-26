package com.example.architecturepractice.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.architecturepractice.LiveDataTestUtil
import com.example.architecturepractice.MainCoroutineRule
import com.example.architecturepractice.customview.model.ShareViewModel
import com.example.architecturepractice.data.FakeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

@ExperimentalCoroutinesApi
class ShareViewModelTest {

    private lateinit var shareViewModel: ShareViewModel
    private val fakeRepository = FakeRepository()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val selectCalendar = Calendar.getInstance()
    private val currentCalendar = Calendar.getInstance()
    private var curYear by Delegates.notNull<Int>()
    private var curMonth by Delegates.notNull<Int>()

    @Before
    fun setUpShareViewModelTest() {
        shareViewModel = ShareViewModel(fakeRepository)

        curYear = currentCalendar.get(Calendar.YEAR)
        curMonth = currentCalendar.get(Calendar.MONTH)

        selectCalendar.set(Calendar.MONTH, curMonth+1)
    }

    @Test
    fun testGetPosition() {
        val diffMonth = shareViewModel.getPosition(selectCalendar, currentCalendar)
        assertEquals(diffMonth, 1)
    }

    @Test
    fun testCheckCalendarListLiveData(): Unit = runBlockingTest {
        val myCalendarList = listOf<String>("holiday_calendar", "naver_calendar")
        shareViewModel.setCheckCalendarList(myCalendarList)
        assertEquals(LiveDataTestUtil.getValue(shareViewModel.checkCalendarListLiveData), myCalendarList)
    }
}