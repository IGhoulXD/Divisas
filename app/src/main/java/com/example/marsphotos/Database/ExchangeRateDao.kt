package com.example.marsphotos.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExchangeRateDao {

    @Insert
    suspend fun insertExchangeRates(exchangeRates: ExchangeRate)

    @Query("SELECT * FROM exchange_rate LIMIT 1")
    suspend fun getExchangeRates(): ExchangeRate
}