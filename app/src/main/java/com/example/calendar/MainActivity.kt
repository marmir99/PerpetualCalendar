package com.example.calendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import android.widget.TextView
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    var year : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val year_picker: NumberPicker = findViewById(R.id.numberPicker)
        year_picker.minValue = 1900
        year_picker.maxValue = 2200
        year_picker.wrapSelectorWheel = true

        val status_Ash_Wednesday: TextView = findViewById(R.id.date_Ash)
        val status_Easter: TextView = findViewById(R.id.date_Easter)
        val status_Corpus_Christi: TextView = findViewById(R.id.date_CorpusChristi)
        val status_Advent: TextView = findViewById(R.id.date_Advent)

        year = year_picker.minValue

        year_picker.setOnValueChangedListener() { picker, oldVal, newVal ->  
            year = newVal
            calculateAndSet(year, status_Advent, status_Corpus_Christi, status_Ash_Wednesday, status_Easter)
        }
    }

    fun calculateAndSet(year : Int, statusAdwent : TextView, status_Corpus_Christi : TextView, status_Ash_Wednesday : TextView, status_Advent : TextView) {
        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        var easter_date = HolidaysCalculator.count_easter(year)
        var easter_day = easter_date.get(0)
        var easter_month = easter_date.get(1)
        var easter_l_date = LocalDate.of(year, easter_month, easter_day)
        var easter_l_date_show = easter_l_date.format(formatter)

        var corpus_christi_date = HolidaysCalculator.count_corpus_christi(easter_l_date).format(formatter)
        var ash_wednesday_date = HolidaysCalculator.count_ash_wednesday(easter_l_date).format(formatter)
        var advent_date = HolidaysCalculator.count_advent(year).format(formatter)

        status_Advent.text = "$easter_l_date_show"
        status_Corpus_Christi.text = "$corpus_christi_date"
        status_Ash_Wednesday.text = "$ash_wednesday_date"
        statusAdwent.text = "$advent_date"
    }

    fun goToWorkingDays(v: View) {
        val i = Intent(this, WorkingDays::class.java)
        startActivity(i)
    }

    fun goToSundays(v: View) {
        val i = Intent(this, Sundays::class.java)
        i.putExtra("year", year)
        i.putExtra("easter", HolidaysCalculator.count_easter(year))
        startActivity(i)
    }

}