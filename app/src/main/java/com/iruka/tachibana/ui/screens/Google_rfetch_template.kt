package com.iruka.tachibana.ui.screens


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.time.LocalDate
// import com.google.api.services.calendar.CalendarScopes
// import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
// import com.google.api.client.util.ExponentialBackOff
// import com.google.api.services.calendar.model.*


suspend fun fetchCalendarEvents(token: String): Map<LocalDate, List<String>> {
    val url = URL("https://www.googleapis.com/calendar/v3/calendars/primary/events")
    val connection = withContext(Dispatchers.IO) {
        (url.openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
            setRequestProperty("Authorization", "Bearer $token")
            connectTimeout = 5000
            readTimeout = 5000
        }
    }

    val response = withContext(Dispatchers.IO) {
        connection.inputStream.bufferedReader().use { it.readText() }
    }

    val json = JSONObject(response)
    val result = mutableMapOf<LocalDate, MutableList<String>>()
    val items = json.getJSONArray("items")

    for (i in 0 until items.length()) {
        val item = items.getJSONObject(i)
        val title = item.optString("summary", "（無題）")
        val start = item.getJSONObject("start")
        val dateStr = start.optString("date") ?: start.optString("dateTime", null)

        if (!dateStr.isNullOrBlank()) {
            val date = LocalDate.parse(dateStr.substring(0, 10)) // yyyy-MM-dd
            result.getOrPut(date) { mutableListOf() }.add(title)
        }
    }

    return result
}