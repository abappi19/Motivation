package io.github.abappi19.motivation.calendar.presentation

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(text = "Select Date Range")
                DatePickerFieldToModal(
                    title = "Start Date",
                    selectedDate = uiState.startDate!!,
                    onDateSelected = {
                        viewModel.onAction(ConfigurationAction.OnStartDateChange(it))
                    },
                )
                DatePickerFieldToModal(
                    title = "End Date",
                    selectedDate = uiState.endDate!!,
                    onDateSelected = {
                        viewModel.onAction(ConfigurationAction.OnEndDateChange(it))
                    }
                )
                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        viewModel.onAction(ConfigurationAction.OnConfigSave(context))
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(when(uiState.savedConfig) {
                        null -> "Add Widget"
                        else -> "Update Widget"
                    })
                }

            }
        }
    }
}
