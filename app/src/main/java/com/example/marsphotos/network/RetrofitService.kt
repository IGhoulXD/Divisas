package com.example.marsphotos.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    private const val BASE_URL = "https://v6.exchangerate-api.com/v6/c606765ebd98eaae4d19c6cf/"

    val api: ExchangeRateApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Usamos GsonConverterFactory
            .build()
            .create(ExchangeRateApi::class.java)
    }
}
