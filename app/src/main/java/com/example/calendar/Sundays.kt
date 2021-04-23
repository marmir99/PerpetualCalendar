package com.example.calendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.example.calendar.HolidaysCalculator

class Sundays : AppCompatActivity() {

    private lateinit var sundays_list : ListView
    var easter = intArrayOf(1,1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sundays)

        val extras = intent.extras ?: return
        val year = extras.getInt("year")
        easter = extras.getIntArray("easter")!!

        val sundays_title : TextView = findViewById(R.id.sundays_title)
        sundays_title.text = "Niedziele handlowe w roku $year"

        sundays_list = findViewById<ListView>(R.id.list)

        if (year < 2020) {
            val itemsList = arrayOf("Podano za wczesny rok", "Podaj rok co najmniej 2020")
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, itemsList)
            sundays_list.adapter = adapter
        }
        else {
            val itemsList = HolidaysCalculator.calculateSundays(year, easter)
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, itemsList)
            sundays_list.adapter = adapter
        }
    }


    fun goBack(v: View) {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

}