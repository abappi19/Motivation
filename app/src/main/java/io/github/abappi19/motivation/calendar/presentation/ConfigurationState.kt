package io.github.abappi19.motivation.calendar.presentation

import io.github.abappi19.motivation.calendar.domain.CalendarWidgetConfig
import io.github.abappi19.motivation.calendar.domain.DotConfig
import java.time.LocalDate

data class ConfigurationState(
    val isLoading: Boolean = true,
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
    val pastConfig: DotConfig? = null,
    val todayConfig: DotConfig? = null,
    val futureConfig: DotConfig? = null,
    val backgroundColor: Long? = null,
    val dotSize: Float? = null,
    val savedConfig: CalendarWidgetConfig? = null
)
