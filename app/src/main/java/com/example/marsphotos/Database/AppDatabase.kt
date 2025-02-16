package com.example.marsphotos.Database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ExchangeRate::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun exchangeRateDao(): ExchangeRateDao
}
