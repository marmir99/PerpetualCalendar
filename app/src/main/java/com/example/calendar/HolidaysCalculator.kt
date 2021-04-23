package com.example.calendar

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.floor

class HolidaysCalculator {
    companion object {
        fun count_easter(year: Int): IntArray {
            var a = year % 19
            var b = floor((year/100).toDouble())
            var c = year % 100
            var d = floor(b/4)
            var e = b % 4
            var f = floor((b+8)/25)
            var g = floor((b-f+1)/3)
            var h = (19*a + b - d - g + 15) % 30
            var i = floor((c/4).toDouble())
            var k = c % 4
            var l = (32 + 2*e + 2*i - h - k) % 7
            var m = floor((a + 11*h + 22*l) / 451)
            var p = (h + l - 7*m + 114) % 31
            var day = p + 1
            var month = floor((h + l - 7*m + 114) / 31)

            var result = intArrayOf(day.toInt(), month.toInt())
            return result
        }

        fun count_advent(year: Int): LocalDate {
            var date = LocalDate.of(year, 12, 25)
            var day = date.dayOfWeek.value
            var advent_date = date.minusDays((21+day).toLong())
            return advent_date
        }

        fun count_corpus_christi(easter: LocalDate): LocalDate {
            var corpus_christi_date = easter.plusDays(60)
            return corpus_christi_date
        }

        fun count_ash_wednesday(easter: LocalDate): LocalDate {
            var ash_wednesday_date = easter.minusDays(46)
            return ash_wednesday_date
        }

        fun calculate_calendar_days(dateBegin : LocalDate, dateEnd : LocalDate) : Long {
            var days = ChronoUnit.DAYS.between(dateBegin, dateEnd)
            return days+1
        }

        fun calculate_working_days (date_begin: LocalDate, date_end : LocalDate, num_days : Long) : Long {
            var days = num_days
            var date_begin_tmp = date_begin
            var date_end_tmp = date_end

            while (date_begin_tmp.dayOfWeek.value != 1){
                if (date_begin_tmp.dayOfWeek.value == 6 || date_begin_tmp.dayOfWeek.value == 7)
                    days -= 1
                date_begin_tmp = date_begin_tmp.plusDays(1)
            }
            while (date_end_tmp.dayOfWeek.value != 1){
                if (date_end_tmp.dayOfWeek.value == 6 || date_end_tmp.dayOfWeek.value == 7)
                    days -= 1
                date_end_tmp = date_end_tmp.minusDays(1)
            }

            var days_tmp = ChronoUnit.DAYS.between(date_begin_tmp, date_end_tmp)
            if (days_tmp > 0)
                days -= ((days_tmp / 7) * 2).toInt()

            for (year in date_begin.year..date_end.year) {
                var first_january = LocalDate.of(year, 1, 1)
                if (first_january >= date_begin && first_january <= date_end && first_january.dayOfWeek.value <= 5)
                    days -= 1
                var sixth_january = LocalDate.of(year, 1, 6)
                if (sixth_january >= date_begin && sixth_january <= date_end && sixth_january.dayOfWeek.value <= 5)
                    days -= 1
                var easter = count_easter(year)
                var easter_day = easter.get(0)
                var easter_month = easter.get(1)
                var easter_date = LocalDate.of(year, easter_month, easter_day)
                easter_date = easter_date.plusDays(1)
                if (easter_date >= date_begin && easter_date <= date_end)
                    days -= 1
                var corpus_christi = count_corpus_christi(easter_date)
                if (corpus_christi >= date_begin && corpus_christi <= date_end)
                    days -= 1
                var first_may = LocalDate.of(year, 5, 1)
                if (first_may >= date_begin && first_may <= date_end && first_may.dayOfWeek.value <=5)
                    days -=1
                var third_may = LocalDate.of(year, 5, 3)
                if (third_may >= date_begin && third_may <= date_end && third_may.dayOfWeek.value <= 5)
                    days -= 1
                var august = LocalDate.of(year, 8, 15)
                if (august >= date_begin && august <= date_end && august.dayOfWeek.value <= 5)
                    days -= 1
                var first_november = LocalDate.of(year, 11, 1)
                if (first_november >= date_begin && first_november <= date_end && first_november.dayOfWeek.value <= 5)
                    days -= 1
                var eleventh_november = LocalDate.of(year, 11, 11)
                if (eleventh_november >= date_begin && eleventh_november <= date_end && eleventh_november.dayOfWeek.value <= 5)
                    days -= 1
                var christmas_first_sunday = LocalDate.of(year, 12, 25)
                if (christmas_first_sunday >= date_begin && christmas_first_sunday <= date_end && christmas_first_sunday.dayOfWeek.value <= 5)
                    days -= 1
                var christmas_second_sunday = LocalDate.of(year, 12, 26)
                if (christmas_second_sunday >= date_begin && christmas_second_sunday <= date_end && christmas_second_sunday.dayOfWeek.value <= 5)
                    days -= 1
            }
            return days
        }

        fun calculateSundays(year: Int, easter: IntArray): Array<String> {
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

            var last_january = LocalDate.of(year, 1, 31)
            if (last_january.dayOfWeek.value != 7) {
                last_january = last_january.minusDays(last_january.dayOfWeek.value.toLong())
            }
            var last_april = LocalDate.of(year, 4, 30)
            if (last_april.dayOfWeek.value != 7) {
                last_april = last_april.minusDays(last_april.dayOfWeek.value.toLong())
            }
            var last_june = LocalDate.of(year, 6, 30)
            if (last_june.dayOfWeek.value != 7) {
                last_june = last_june.minusDays(last_june.dayOfWeek.value.toLong())
            }
            var last_august = LocalDate.of(year, 8, 31)
            if (last_august.dayOfWeek.value != 7) {
                last_august = last_august.minusDays(last_august.dayOfWeek.value.toLong())
            }
            var christmas = LocalDate.of(year, 12, 25)
            var first_before_christmas = christmas.minusDays(christmas.dayOfWeek.value.toLong() + 7)
            var second_before_christmas = christmas.minusDays(christmas.dayOfWeek.value.toLong())

            var month = easter.get(1).toInt()
            var day = easter.get(0).toInt()
            var easter = LocalDate.of(year, month, day)
            easter = easter.minusDays(7)

            val result = arrayOf(last_january.format(formatter), easter.format(formatter), last_april.format(formatter), last_june.format(formatter),
                    last_august.format(formatter), first_before_christmas.format(formatter), second_before_christmas.format(formatter))
            return result
        }
    }

}