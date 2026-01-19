package io.github.abappi19.motivation.core.domain

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow


val Context.dataStore by preferencesDataStore(name = "widget_prefs")

 interface WidgetRepository <T: WidgetConfig>{

    fun getWidgetConfig(widgetId: Int): Flow<T?>

    fun getPreferencesKey(name:String, widgetId:Int): Preferences.Key<String>{
        return stringPreferencesKey("widget_sp-$name-$widgetId")
    }

    suspend fun updateWidgetConfig(widgetId: Int, config: T)
}