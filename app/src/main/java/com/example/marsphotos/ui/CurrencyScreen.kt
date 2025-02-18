package com.example.marsphotos.ui

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.work.*
import com.example.marsphotos.database.ExchangeRate
import com.example.marsphotos.sync.ExchangeRateWorker
import com.example.marsphotos.viewmodel.CurrencyViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@Composable
fun CurrencyScreen(viewModel: CurrencyViewModel, context: Context) {
    val rates by viewModel.exchangeRates.collectAsState()
    val isLoading = remember { mutableStateOf(false) }
    val time = remember { mutableStateOf(getCurrentTime()) }
    val errorMessage = remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    // Estado para alternar entre las vistas
    val showHistory = remember { mutableStateOf(false) }

    // Iniciar WorkManager para sincronización automática
    LaunchedEffect(Unit) {
        val workRequest = PeriodicWorkRequestBuilder<ExchangeRateWorker>(1, TimeUnit.HOURS).build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "exchange_rate_sync",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    // Actualizar la hora cada segundo
    LaunchedEffect(Unit) {
        while (true) {
            time.value = getCurrentTime()
            kotlinx.coroutines.delay(1000L)
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Divisas", style = MaterialTheme.typography.headlineMedium)
        Text("Hora: ${time.value}", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para actualizar las tasas
        Button(onClick = {
            coroutineScope.launch {
                isLoading.value = true
                errorMessage.value = null
                viewModel.fetchExchangeRates {
                    errorMessage.value = "Error al cargar las tasas de cambio"
                }
                isLoading.value = false
            }
        }) {
            Text("Actualizar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para mostrar historial
        Button(onClick = {
            showHistory.value = !showHistory.value
        }) {
            Text(if (showHistory.value) "Ver Tasas Actuales" else "Ver Historial")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar tasas actuales o historial
        if (isLoading.value) {
            Text("Cargando tasas de cambio...", style = MaterialTheme.typography.bodyLarge)
        } else if (errorMessage.value != null) {
            Text(errorMessage.value!!, style = MaterialTheme.typography.bodyLarge)
        } else if (showHistory.value) {
            // Mostrar historial
            LazyColumn {
                items(rates) { rate ->
                    Text("${rate.currency}: ${rate.rate}", style = MaterialTheme.typography.bodyLarge)
                }
            }
        } else {
            // Mostrar tasas actuales
            Text("1 Peso Mexicano equivale a:", style = MaterialTheme.typography.bodyLarge)
            rates.find { it.currency == "USD" }?.let {
                Text("\uD83C\uDDFA\uD83C\uDDF8 ${it.rate} USD", style = MaterialTheme.typography.bodyLarge)
            }
            rates.find { it.currency == "CAD" }?.let {
                Text("\uD83C\uDDE8\uD83C\uDDE6 ${it.rate} CAD", style = MaterialTheme.typography.bodyLarge)
            }
            rates.find { it.currency == "EUR" }?.let {
                Text("\uD83C\uDDEA\uD83C\uDDFA ${it.rate} EUR", style = MaterialTheme.typography.bodyLarge)
            }
            rates.find { it.currency == "COP" }?.let {
                Text("\uD83C\uDDE8\uD83C\uDDF4 ${it.rate} COP", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

fun getCurrentTime(): String {
    val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return sdf.format(Date())
}
