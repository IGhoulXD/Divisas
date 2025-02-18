package com.example.marsphotos.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsphotos.network.RetrofitService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ExchangeRate(val currency: String, val rate: Double)

class CurrencyViewModel : ViewModel() {

    private val _exchangeRates = MutableStateFlow<List<ExchangeRate>>(emptyList())
    val exchangeRates: StateFlow<List<ExchangeRate>> = _exchangeRates

    // Función para obtener las tasas de cambio desde la API
    fun fetchExchangeRates(onError: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                // Llamadas para obtener las tasas de cambio entre MXN y las otras divisas
                val usdRate = RetrofitService.api.getExchangeRates("MXN", "USD")
                val cadRate = RetrofitService.api.getExchangeRates("MXN", "CAD")
                val eurRate = RetrofitService.api.getExchangeRates("MXN", "EUR")
                val copRate = RetrofitService.api.getExchangeRates("MXN", "COP")

                // Crear la lista de tasas de cambio
                _exchangeRates.value = listOf(
                    ExchangeRate("USD", usdRate.rate),
                    ExchangeRate("CAD", cadRate.rate),
                    ExchangeRate("EUR", eurRate.rate),
                    ExchangeRate("COP", copRate.rate)
                )

                Log.d("CurrencyViewModel", "Exchange rates updated: ${_exchangeRates.value}")
            } catch (e: Exception) {
                Log.e("CurrencyViewModel", "Error fetching exchange rates", e)
                onError()
            }
        }
    }
}
