package io.github.abappi19.motivation.calendar.presentation

import android.content.Context
import androidx.glance.appwidget.updateAll
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.abappi19.motivation.calendar.data.repository.DefaultCalendarWidgetRepository
import io.github.abappi19.motivation.calendar.domain.CalendarWidgetConfig
import io.github.abappi19.motivation.calendar.widget.MotivationWidget
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConfigurationViewModel(
    private val repository: DefaultCalendarWidgetRepository,
    private val widgetId: Int,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConfigurationState())
    val uiState: StateFlow<ConfigurationState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getWidgetConfig(widgetId).collect { config ->
                _uiState.value = ConfigurationState(
                    isLoading = false,
                    startDate = config.startDate,
                    endDate = config.endDate,
                    pastConfig = config.pastConfig,
                    todayConfig = config.todayConfig,
                    futureConfig = config.futureConfig,
                    backgroundColor = config.backgroundColor,
                    dotSize = config.dotSize,
                    savedConfig = config
                )
            }
        }
    }

    fun onAction(action: ConfigurationAction) {
        when (action) {
            is ConfigurationAction.OnConfigSave -> {
                val newConfig  = CalendarWidgetConfig(
                    startDate = _uiState.value.startDate,
                    endDate = _uiState.value.endDate,
                    pastConfig = _uiState.value.pastConfig,
                    todayConfig = _uiState.value.todayConfig,
                    futureConfig = _uiState.value.futureConfig,
                    backgroundColor = _uiState.value.backgroundColor,
                    dotSize = _uiState.value.dotSize
                )

                saveConfig(action.context)

                _uiState.update { it.copy(
                    savedConfig = newConfig
                ) }
            }
            else -> Unit
        }
    }


     fun saveConfig(context: Context) {
        viewModelScope.launch {
            repository.updateWidgetConfig(widgetId,
                config = CalendarWidgetConfig(
                startDate = _uiState.value.startDate,
                endDate = _uiState.value.endDate,
                pastConfig = _uiState.value.pastConfig,
                todayConfig = _uiState.value.todayConfig,
                futureConfig = _uiState.value.futureConfig,
                backgroundColor = _uiState.value.backgroundColor,
                dotSize = _uiState.value.dotSize
            ))
            MotivationWidget().updateAll(context)
        }
    }
}
