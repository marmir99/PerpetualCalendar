package com.example.calendar

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import java.time.LocalDate

class WorkingDays : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_working_days)

        val date_begin: DatePicker = findViewById(R.id.date_begin)
        val date_end: DatePicker = findViewById(R.id.date_end)
        val calendar: TextView = findViewById(R.id.num_days_cal)
        val working: TextView = findViewById(R.id.num_days_work)

        date_begin.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            var date_begin = LocalDate.of(year, monthOfYear + 1, dayOfMonth)
            var date_end = LocalDate.of(date_end.year, date_end.month + 1, date_end.dayOfMonth)
            calculateAndSet(date_begin, date_end, calendar, working)
        }

        date_end.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            var date_begin = LocalDate.of(date_begin.year, date_begin.month + 1, date_begin.dayOfMonth)
            var date_end = LocalDate.of(year, monthOfYear + 1, dayOfMonth)
            calculateAndSet(date_begin, date_end, calendar, working)
        }
    }

    fun calculateAndSet(date_begin : LocalDate, date_end : LocalDate, calendar : TextView, working : TextView) {
        var numDays = HolidaysCalculator.calculate_calendar_days(date_begin, date_end) //pierwszy oraz ostatni dzień są wliczane
        if (numDays >= 0) {
            calendar.text = "$numDays"
            var numWorkDays = HolidaysCalculator.calculate_working_days(date_begin, date_end, numDays)
            working.text = "$numWorkDays"
        }
        else {
            calendar.text = "Error"
            working.text = "Error"
        }
    }


    fun goBack(v: View) {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }
}

