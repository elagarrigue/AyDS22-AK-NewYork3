package ayds.newyork.songinfo.home.view

import ayds.newyork.songinfo.home.model.repository.local.spotify.DatePrecision
import java.text.SimpleDateFormat
import java.util.*

private const val DEFAULT_RESULT = "N/A"
private const val NEW_MONTH_FORMAT = "MMMM, yyyy"
private const val NEW_DAY_FORMAT = "dd/MM/yyyy"

interface DateFormatter{
    fun getReleaseDate(date: String, precisionDate: DatePrecision) : String
}

internal class DateFormatterImpl : DateFormatter{

    override fun getReleaseDate(date: String, precisionDate: DatePrecision) =
        when (precisionDate) {
            DatePrecision.DAY -> getDayFormat(date)
            DatePrecision.MONTH -> getMonthFormat(date)
            DatePrecision.YEAR -> getYearFormat(date)
            else -> date
        }

    private fun getYearFormat(year: String): String {
        var format = DEFAULT_RESULT
        if (year.isNotEmpty()) {
            format =
                year + if (isLeapYear(year.toInt())) " (leap year)" else " (not a leap year)"
        }
        return format
    }

    private fun isLeapYear(year: Int) = ((year % 4 == 0) && (year % 100 != 0 || year % 400 == 0))

    private fun getMonthFormat(date: String): String {
        val monthFormat = SimpleDateFormat("yyyy-MM", Locale.US)
        return applyFormat(monthFormat, NEW_MONTH_FORMAT, date)
    }

    private fun getDayFormat(date: String): String {
        val dayFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return applyFormat(dayFormat, NEW_DAY_FORMAT, date)
    }

    private fun applyFormat(
        dateFormat: SimpleDateFormat,
        newDateFormat: String,
        date: String
    ): String {
        var format = DEFAULT_RESULT
        val releaseDate = dateFormat.parse(date)

        releaseDate?.let{
            dateFormat.applyPattern(newDateFormat)
            format = dateFormat.format(releaseDate)
        }

        return format
    }
}

