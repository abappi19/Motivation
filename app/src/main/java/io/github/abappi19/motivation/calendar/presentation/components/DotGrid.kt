package io.github.abappi19.motivation.calendar.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalSize
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.unit.ColorProvider
import io.github.abappi19.motivation.calendar.domain.CalendarWidgetConfig
import java.time.LocalDate

@Composable
fun DotGrid(config: CalendarWidgetConfig?){
    val size = LocalSize.current

    val dotSize =  12f
    val spacing = 2f;
    val dotsPerRow = ((size.width / (dotSize.dp + spacing.dp))).toInt()
    val today = LocalDate.now()

    val dates = remember(config?.startDate, config?.endDate) {
        generateSequence(config?.startDate) { date ->
            val next = date.plusDays(1) // Use .plus(1, DateTimeUnit.DAY) for kotlinx-datetime
            if (next <= config?.endDate) next else null
        }.toList().chunked(dotsPerRow)
    }

    Column(
        modifier = GlanceModifier.fillMaxWidth()
            .background(GlanceTheme.colors.widgetBackground)
            .padding(spacing.dp/2),
    ){
       dates.forEach { row->
           Row(
               modifier = GlanceModifier.fillMaxWidth()
                   .padding(horizontal = spacing.dp /2),
               horizontalAlignment = Alignment.CenterHorizontally,
           ) {
               row.forEach { date ->
                   Box(
                       modifier = GlanceModifier.size(size = dotSize.dp)
                           .padding(horizontal = spacing.dp /2)
                           .background(colorProvider = if( date < today ) GlanceTheme.colors.primary else GlanceTheme.colors.secondary)
                   ){}
               }
           }
       }
    }
}

