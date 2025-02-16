package com.example.marsphotos.Database


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exchange_rate")
data class ExchangeRate(
    @PrimaryKey val id: String , // Solo un registro con un ID único
    val rates: String, // Este es un String que puedes convertir a Map cuando lo necesites
    val timestamp: Long
)