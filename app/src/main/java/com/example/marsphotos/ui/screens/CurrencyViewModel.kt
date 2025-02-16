package com.example.marsphotos

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsphotos.network.CurrencyRepository
import com.example.marsphotos.network.RetrofitService
import kotlinx.coroutines.launch
import retrofit2.HttpException

class CurrencyViewModel(repository: CurrencyRepository) : ViewModel() {
    private val _exchangeRates = mutableStateOf<Map<String, Double>>(emptyMap())
    val exchangeRates: Map<String, Double> get() = _exchangeRates.value

    private val _isLoading = mutableStateOf(false)
    val isLoading: Boolean get() = _isLoading.value

    init {
        fetchExchangeRates()
    }

    fun fetchExchangeRates() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitService.api.getExchangeRates()
                _exchangeRates.value = response.rates
            } catch (e: HttpException) {
                // Manejo de error (en caso de fallo)
                _exchangeRates.value = emptyMap()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
