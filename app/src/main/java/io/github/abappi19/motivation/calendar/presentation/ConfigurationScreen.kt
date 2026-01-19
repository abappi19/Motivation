package io.github.abappi19.motivation.calendar.presentation

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import io.github.abappi19.motivation.calendar.data.repository.DefaultCalendarWidgetRepository
import io.github.abappi19.motivation.core.presentation.components.DatePickerFieldToModal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigurationScreen(widgetId: Int) {
    val context = LocalContext.current
    val repository = remember { DefaultCalendarWidgetRepository(context) }
    val viewModel = remember { ConfigurationViewModel(repository, widgetId) }
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Configure Widget") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DatePickerFieldToModal(

            )
            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.saveConfig(context)
                    val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
                    (context as? Activity)?.setResult(Activity.RESULT_OK, resultValue)
                    (context as? Activity)?.finish()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save and Add Widget")
            }
        }
    }
}
