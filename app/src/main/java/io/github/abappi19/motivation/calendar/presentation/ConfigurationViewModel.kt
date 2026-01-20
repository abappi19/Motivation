package io.github.abappi19.motivation.calendar.presentation

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import androidx.glance.appwidget.updateAll
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.abappi19.motivation.calendar.data.repository.DefaultCalendarWidgetRepository
import io.github.abappi19.motivation.calendar.domain.CalendarWidgetConfig
import io.github.abappi19.motivation.calendar.domain.DotConfig
import io.github.abappi19.motivation.calendar.widget.MotivationWidget
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class ConfigurationViewModel(
    private val repository: DefaultCalendarWidgetRepository,
    private val widgetId: Int,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConfigurationState())
    val uiState: StateFlow<ConfigurationState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getWidgetConfig(widgetId).collect { config ->
                _uiState.value =
                    ConfigurationState(
                        isLoading = false,
                        startDate = config?.startDate ?: LocalDate.now().withDayOfYear(1),
                        endDate = config?.endDate ?: LocalDate.now().withDayOfYear(1).plusYears(1).minusDays(1),
                        pastConfig = config?.pastConfig ?: DotConfig(),
                        todayConfig = config?.todayConfig ?: DotConfig(),
                        futureConfig = config?.futureConfig ?: DotConfig(),
                        backgroundColor = config?.backgroundColor ?: 0xffffff,
                        dotSize = config?.dotSize ?: 12f,
                        savedConfig = config
                    )

            }
        }
    }

    fun onAction(action: ConfigurationAction) {
        when (action) {
            is ConfigurationAction.OnStartDateChange -> {
                _uiState.update { it.copy(startDate = action.newDate) }
            }

            is ConfigurationAction.OnEndDateChange -> {
                _uiState.update { it.copy(endDate = action.newDate) }
            }

            is ConfigurationAction.OnConfigSave -> {
                val newConfig = CalendarWidgetConfig(
                    startDate = _uiState.value.startDate,
                    endDate = _uiState.value.endDate,
                    pastConfig = _uiState.value.pastConfig,
                    todayConfig = _uiState.value.todayConfig,
                    futureConfig = _uiState.value.futureConfig,
                    backgroundColor = _uiState.value.backgroundColor,
                    dotSize = _uiState.value.dotSize
                )

                saveConfig(action.context, newConfig)
            }

            else -> Unit
        }
    }


    fun saveConfig(context: Context, config: CalendarWidgetConfig) {
        viewModelScope.launch {
            repository.updateWidgetConfig(
                widgetId,
                config = config
            )
            MotivationWidget().updateAll(context)

            val resultValue =
                Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
            (context as? Activity)?.setResult(Activity.RESULT_OK, resultValue)
            (context as? Activity)?.finish()
        }
    }
}
