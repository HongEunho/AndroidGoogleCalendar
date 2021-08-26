package com.example.architecturepractice.calendar
import android.os.Bundle
import android.util.Log
import com.example.architecturepractice.R
import com.example.architecturepractice.databinding.ActivityCalendarBinding
import dagger.android.support.DaggerAppCompatActivity

class CalendarActivity : DaggerAppCompatActivity() {

    private lateinit var binding: ActivityCalendarBinding
    private fun getViewBinding(): ActivityCalendarBinding = ActivityCalendarBinding.inflate(layoutInflater)

    private val autoLogin: Boolean by lazy {
        intent.getBooleanExtra(getString(R.string.autoLogin), false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        initViews()
    }

    private fun initViews(): Unit = with(binding) {
        setTransaction()
    }

    private fun setTransaction() {
        val bundle = Bundle()
        bundle.putBoolean(getString(R.string.autoLogin), autoLogin)

        val calendarFragment = CalendarFragment()
        calendarFragment.arguments = bundle

        val transaction = supportFragmentManager.beginTransaction()
            .replace(binding.calendarContainerView.id, calendarFragment)
        transaction.commit()
    }

}