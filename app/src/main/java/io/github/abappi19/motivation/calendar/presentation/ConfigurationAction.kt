package io.github.abappi19.motivation.calendar.presentation

import android.content.Context
import io.github.abappi19.motivation.calendar.domain.CalendarWidgetConfig
import java.time.LocalDate

sealed interface ConfigurationAction {
    data class OnStartDateChange(val newDate: LocalDate): ConfigurationAction
    data class OnEndDateChange(val newDate: LocalDate): ConfigurationAction
    data class OnConfigSave(val context: Context): ConfigurationAction
}