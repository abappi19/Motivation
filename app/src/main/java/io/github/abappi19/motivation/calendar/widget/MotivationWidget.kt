package io.github.abappi19.motivation.calendar.widget

import android.content.Context
import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.getAppWidgetState
import androidx.glance.layout.*
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import io.github.abappi19.motivation.calendar.data.repository.DefaultCalendarWidgetRepository
import io.github.abappi19.motivation.calendar.domain.CalendarWidgetConfig
import io.github.abappi19.motivation.calendar.presentation.components.DotGrid
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last

class MotivationWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, glanceId: GlanceId) {
        val repository = DefaultCalendarWidgetRepository(context)
        val manager = GlanceAppWidgetManager(context)
        val widgetId = manager.getAppWidgetId(glanceId)

        provideContent {

            val config = repository.getWidgetConfig(widgetId).collectAsState(CalendarWidgetConfig())
            GlanceTheme{
                DotGrid(config.value)
            }
        }
    }
}

class MotivationWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = MotivationWidget()
}
