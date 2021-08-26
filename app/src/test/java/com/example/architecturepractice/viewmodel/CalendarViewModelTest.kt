package com.example.architecturepractice.viewmodel

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModelProvider
import com.example.architecturepractice.*
import com.example.architecturepractice.calendar.CalendarViewModel
import com.example.architecturepractice.calendar.LoadingState
import com.example.architecturepractice.data.FakeRepository
import com.example.architecturepractice.di.AppViewModelFactory
import com.example.architecturepractice.di.DaggerTestApplicationComponent
import com.example.architecturepractice.di.TestApplicationComponent
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import org.junit.Assert.*


@ExperimentalCoroutinesApi
class CalendarViewModelTest {

    @Inject
    lateinit var preferenceManager: PreferenceManager

    private lateinit var calendarViewModel: CalendarViewModel
    private val fakeRepository = FakeRepository()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUpCalendarViewModelTest() {
        val component: TestApplicationComponent = DaggerTestApplicationComponent.factory()
            .create(TestApplication())
        component.inject(this)
        calendarViewModel = CalendarViewModel(fakeRepository, preferenceManager)
    }

    @Test
    fun testFetchLocalData(): Unit = runBlockingTest {
        assertEquals(LiveDataTestUtil.getValue(calendarViewModel.loadingStateLiveData), LoadingState.UnInitialized)
        calendarViewModel.fetchLocalData()
        assertEquals(LiveDataTestUtil.getValue(calendarViewModel.loadingStateLiveData), LoadingState.Success)
    }

    @Test
    fun testFetchData(): Unit = runBlockingTest {
        assertEquals(LiveDataTestUtil.getValue(calendarViewModel.loadingStateLiveData), LoadingState.UnInitialized)
        calendarViewModel.fetchData()
        assertEquals(LiveDataTestUtil.getValue(calendarViewModel.loadingStateLiveData), LoadingState.Success)
    }

    @Test
    fun testCalendarIdLiveData(): Unit = runBlockingTest {
        val testCalendarList = calendarViewModel.getLocalCalendarList()
        calendarViewModel.setCalendarIdList(testCalendarList)
        assertEquals(LiveDataTestUtil.getValue(calendarViewModel.calendarIdLiveData), testCalendarList)
    }

}