package com.example.marsphotos.network

data class ExchangeRateResponse(
    val base: String,
    val rates: Map<String, Double>
)
