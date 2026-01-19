package io.github.abappi19.motivation.calendar.widget

import android.content.Context
import android.content.res.Resources
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.*
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import io.github.abappi19.motivation.calendar.data.repository.DefaultCalendarWidgetRepository
import kotlinx.coroutines.flow.first

class MotivationWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val repository = DefaultCalendarWidgetRepository(context)
        // For simplicity in MVP, we use a fixed ID or first config
        // In full implementation, map GlanceId to widgetId
        val config = repository.getWidgetConfig(0).first()

        provideContent {
            Column(
                modifier = GlanceModifier.fillMaxSize().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Start: ${config.startDate}",
                    style = TextStyle(color = GlanceTheme.colors.primary)
                )
                Text(
                    text = "End: ${config.endDate}",
                    style = TextStyle(color = GlanceTheme.colors.primary)
                )
            }
        }
    }
}

class MotivationWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = MotivationWidget()
}
