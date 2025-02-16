package com.example.marsphotos

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.marsphotos.Database.AppDatabase
import com.example.marsphotos.Database.ExchangeRateDao
import com.example.marsphotos.network.ExchangeRateApi
import com.example.marsphotos.network.RetrofitService

class MarsPhotosApplication : Application() {

    // Contenedor de dependencias
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        // Inicializa el contenedor de dependencias
        container = DefaultAppContainer(applicationContext)
    }
}

interface AppContainer {
    val exchangeRateDao: ExchangeRateDao
    val exchangeRateApi: ExchangeRateApi
}

class DefaultAppContainer(context: Context) : AppContainer {

    // Inicializa la base de datos Room
    private val appDatabase: AppDatabase by lazy {
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "exchange_rate_db"
        ).build()
    }

    // Inicializa el DAO de ExchangeRate
    override val exchangeRateDao: ExchangeRateDao by lazy {
        appDatabase.exchangeRateDao()
    }

    // Inicializa Retrofit para la API de tasas de cambio
    override val exchangeRateApi: ExchangeRateApi by lazy {
        RetrofitService.api
    }
}
