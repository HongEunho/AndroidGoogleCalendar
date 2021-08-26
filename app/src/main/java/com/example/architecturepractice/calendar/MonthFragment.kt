package com.example.architecturepractice.calendar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.architecturepractice.customview.adapter.MonthAdapter
import com.example.architecturepractice.customview.model.ShareViewModel
import com.example.architecturepractice.databinding.FragmentCalendarBinding
import com.example.architecturepractice.databinding.FragmentMonthBinding
import com.example.architecturepractice.di.AppViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

class MonthFragment: DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private lateinit var sharedViewModel: ShareViewModel
    private lateinit var monthViewModel: MonthViewModel

    private lateinit var monthAdapter: MonthAdapter

    var pageIndex = 0
    private var monthDate = Calendar.getInstance()

    private lateinit var date: Date
    private lateinit var fetchJob: Job
    private var shareDate = Calendar.getInstance()

    private fun getViewBinding(): FragmentMonthBinding =
        FragmentMonthBinding.inflate(layoutInflater)

    private var _binding: FragmentMonthBinding?= null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        monthViewModel = ViewModelProvider(this, viewModelFactory)[MonthViewModel::class.java]
        monthDate.set(Calendar.YEAR, monthViewModel.year)
        monthDate.set(Calendar.MONTH, monthViewModel.month)

        activity?.run {
            sharedViewModel = ViewModelProvider(this, viewModelFactory)[ShareViewModel::class.java]
            shareDate.set(Calendar.YEAR, sharedViewModel.year)
            shareDate.set(Calendar.MONTH, sharedViewModel.month)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = getViewBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDate()
        initAdapter()
        observeData()
    }

    private fun initDate() {
        pageIndex -= (Int.MAX_VALUE / 2)
        date = monthDate.run {
            add(Calendar.MONTH, pageIndex)
            time
        }

        fetchJob = monthViewModel.fetchData(date)
        MainScope().launch {
            binding.monthRecyclerView.setHasFixedSize(true)
            binding.monthRecyclerView.adapter = monthAdapter
        }
    }

    private fun initAdapter() {
        monthAdapter = MonthAdapter(binding.monthViewLayout, date, FragmentCalendarBinding.inflate(layoutInflater))
    }

    private fun observeData() {

        sharedViewModel.checkCalendarListLiveData.observe(viewLifecycleOwner) {
            monthAdapter.setCheckCalenderList(it)
        }

        monthViewModel.curMonthEventListLiveData.observe(viewLifecycleOwner) {
            monthAdapter.setCurMonthEventList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}