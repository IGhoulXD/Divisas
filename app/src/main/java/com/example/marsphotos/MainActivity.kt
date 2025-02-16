package com.example.marsphotos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.example.marsphotos.Database.AppDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configura Room en MainActivity
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "exchange_rate_db"
        ).build()

        // Ahora puedes usar exchangeRateDao desde la base de datos
        val exchangeRateDao = db.exchangeRateDao()

        // Accede a los datos con exchangeRateDao, por ejemplo:
        // CoroutineScope(Dispatchers.IO).launch {
        //    val rates = exchangeRateDao.getExchangeRates()
        //    // Trabaja con los datos
        // }

        setContent {
            // Tu contenido de UI con Jetpack Compose
        }
    }
}
