package io.github.abappi19.motivation.app

import android.appwidget.AppWidgetManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.VerticalAlignmentLine
import io.github.abappi19.motivation.calendar.presentation.ConfigurationScreen
import io.github.abappi19.motivation.core.presentation.theme.MotivationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val widgetId = intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        setContent {
            MotivationTheme {
                if (widgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                    ConfigurationScreen(widgetId = widgetId)
                } else {
                    Scaffold(modifier = Modifier.Companion.fillMaxSize()) { innerPadding ->
                        Row(modifier = Modifier.padding(innerPadding),
                            verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Add the widget from the home screen to configure it.",
                                modifier = Modifier.Companion.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}