package io.github.abappi19.motivation.calendar.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import io.github.abappi19.motivation.calendar.domain.CalendarWidgetConfig
import io.github.abappi19.motivation.calendar.domain.CalendarWidgetRepository
import io.github.abappi19.motivation.core.domain.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DefaultCalendarWidgetRepository(private val context: Context) : CalendarWidgetRepository {
    private val WIDGET_NAME = "calendarWidget";

    override fun getWidgetConfig(widgetId: Int): Flow<CalendarWidgetConfig?> {
        return context.dataStore.data.map { preferences ->
            val key = getPreferencesKey(WIDGET_NAME, widgetId)
            preferences[key]?.let { str ->
                 Json.decodeFromString<CalendarWidgetConfig>(str)
             }
        }
    }

    override suspend fun updateWidgetConfig(
        widgetId: Int,
        config: CalendarWidgetConfig,
    ) {
        context.dataStore.edit { preferences ->
            val key = getPreferencesKey(WIDGET_NAME, widgetId)
            val value = Json.encodeToString(config)
            preferences[key] = value
        }
    }
}
