package io.github.abappi19.motivation.model

import java.time.LocalDate

data class WidgetConfig(
    val startDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate = LocalDate.now().plusMonths(1),
    val pastConfig: DotConfig = DotConfig(),
    val todayConfig: DotConfig = DotConfig(),
    val futureConfig: DotConfig = DotConfig(),
    val backgroundColor: Long = 0xFFFFFFFF,
    val dotSize: Int = 12
)

data class DotConfig(
    val fillColor: Long = 0xFFD1D5DB,
    val borderColor: Long = 0xFF9CA3AF,
    val roundness: Int = 2
)
