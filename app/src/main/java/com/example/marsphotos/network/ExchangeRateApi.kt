package com.example.marsphotos.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.GET

// Interfaz Retrofit para la API de tipo de cambio
interface ExchangeRateApi {

    @GET("latest/MXN")
    suspend fun getExchangeRates(): ExchangeRateResponse
}


