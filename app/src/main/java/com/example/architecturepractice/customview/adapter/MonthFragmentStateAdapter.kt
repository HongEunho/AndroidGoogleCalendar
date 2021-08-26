package com.example.architecturepractice.customview.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.architecturepractice.calendar.MonthFragment

class MonthFragmentStateAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    private val pageCount = Int.MAX_VALUE
    var fragments = mutableListOf<MonthFragment>()
    val monthFragmentPosition = Int.MAX_VALUE / 2
    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun createFragment(position: Int): Fragment {
        val monthFragment = MonthFragment()
        monthFragment.pageIndex = position
        return monthFragment
    }
}