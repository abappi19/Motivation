package io.github.abappi19.motivation.widget

import android.content.Context
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.*
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import io.github.abappi19.motivation.data.WidgetRepository
import io.github.abappi19.motivation.model.WidgetConfig
import kotlinx.coroutines.flow.first

class MotivationWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val repository = WidgetRepository(context)
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
                    style = TextStyle(color = ColorProvider(androidx.compose.ui.graphics.Color.Black))
                )
                Text(
                    text = "End: ${config.endDate}",
                    style = TextStyle(color = ColorProvider(androidx.compose.ui.graphics.Color.Black))
                )
            }
        }
    }
}

class MotivationWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = MotivationWidget()
}
