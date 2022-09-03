package com.udacity.asteroidradar

import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import java.util.*

object Constants {
    const val API_QUERY_DATE_FORMAT = "YYYY-MM-dd"
    var today = getNextSevenDaysFormattedDates().first()
    var endDate = getNextSevenDaysFormattedDates().last()
    const val DEFAULT_END_DATE_DAYS = 7
    const val BASE_URL = "https://api.nasa.gov/"
    const val API_KEY = "" // Enter your own Api key here
}