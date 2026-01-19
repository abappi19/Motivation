package io.github.abappi19.motivation.ui

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import io.github.abappi19.motivation.data.WidgetRepository
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigurationScreen(widgetId: Int) {
    val context = LocalContext.current
    val repository = remember { WidgetRepository(context) }
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
            Text("Start Date: ${uiState.startDate}")
            Button(onClick = { viewModel.updateStartDate(uiState.startDate.minusDays(1)) }) {
                Text("Decrease Start Date")
            }
            Button(onClick = { viewModel.updateStartDate(uiState.startDate.plusDays(1)) }) {
                Text("Increase Start Date")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("End Date: ${uiState.endDate}")
            Button(onClick = { viewModel.updateEndDate(uiState.endDate.minusDays(1)) }) {
                Text("Decrease End Date")
            }
            Button(onClick = { viewModel.updateEndDate(uiState.endDate.plusDays(1)) }) {
                Text("Increase End Date")
            }

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
