package io.github.abappi19.motivation.ui

import android.appwidget.AppWidgetManager
import android.content.Context
import androidx.glance.appwidget.updateAll
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.abappi19.motivation.data.WidgetRepository
import io.github.abappi19.motivation.model.WidgetConfig
import io.github.abappi19.motivation.widget.MotivationWidget
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class ConfigurationViewModel(
    private val repository: WidgetRepository,
    private val widgetId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(WidgetConfig())
    val uiState: StateFlow<WidgetConfig> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getWidgetConfig(widgetId).collect { config ->
                _uiState.value = config
            }
        }
    }

    fun updateStartDate(date: LocalDate) {
        _uiState.value = _uiState.value.copy(startDate = date)
    }

    fun updateEndDate(date: LocalDate) {
        _uiState.value = _uiState.value.copy(endDate = date)
    }

    fun saveConfig(context: Context) {
        viewModelScope.launch {
            repository.updateWidgetConfig(widgetId, _uiState.value)
            MotivationWidget().updateAll(context)
        }
    }
}
