package io.github.abappi19.motivation

import android.appwidget.AppWidgetManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.abappi19.motivation.ui.ConfigurationScreen
import io.github.abappi19.motivation.ui.theme.MotivationTheme

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
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        Text(
                            text = "Add the widget from the home screen to configure it.",
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}
