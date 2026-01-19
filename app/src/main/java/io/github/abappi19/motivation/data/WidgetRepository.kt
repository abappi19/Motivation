package io.github.abappi19.motivation.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import io.github.abappi19.motivation.model.WidgetConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

val Context.dataStore by preferencesDataStore(name = "widget_prefs")

class WidgetRepository(private val context: Context) {
    
    private val START_DATE = stringPreferencesKey("start_date")
    private val END_DATE = stringPreferencesKey("end_date")

    fun getWidgetConfig(widgetId: Int): Flow<WidgetConfig> {
        return context.dataStore.data.map { preferences ->
            WidgetConfig(
                startDate = preferences[START_DATE]?.let { LocalDate.parse(it) } ?: LocalDate.now(),
                endDate = preferences[END_DATE]?.let { LocalDate.parse(it) } ?: LocalDate.now().plusMonths(1)
            )
        }
    }

    suspend fun updateWidgetConfig(widgetId: Int, config: WidgetConfig) {
        context.dataStore.edit { preferences ->
            preferences[START_DATE] = config.startDate.toString()
            preferences[END_DATE] = config.endDate.toString()
        }
    }
}
