package com.example.marsphotos.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.marsphotos.CurrencyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyScreen(viewModel: CurrencyViewModel) {
    val exchangeRates = viewModel.exchangeRates
    val isLoading = viewModel.isLoading

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Título de la pantalla
        TopAppBar(
            title = { Text(text = "Divisas") }
        )

        // Titulo
        Text("Divisa base 1 MXN", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)

        // Mostrar información de tasas de cambio
        if (isLoading) {
            Text("Cargando...", modifier = Modifier.padding(top = 20.dp))
        } else {
            Column(modifier = Modifier.padding(top = 20.dp)) {
                Text("Cambio: USD, CAN, COP, EURO", style = androidx.compose.material3.MaterialTheme.typography.bodyLarge)

                // Mostrar cada tasa de cambio
                exchangeRates.forEach { (currency, rate) ->
                    Text("$currency: ${"%.2f".format(rate)}", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
                }
            }
        }

        // Botón para actualizar datos
        Button(onClick = { viewModel.fetchExchangeRates() }, modifier = Modifier.padding(top = 20.dp)) {
            Text("Actualizar Datos")
        }
    }
}
