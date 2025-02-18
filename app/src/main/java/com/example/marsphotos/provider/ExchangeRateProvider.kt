package com.example.marsphotos.provider

import android.content.*
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import com.example.marsphotos.database.ExchangeRateDatabase

class ExchangeRateProvider : ContentProvider() {
    private lateinit var db: ExchangeRateDatabase

    override fun onCreate(): Boolean {
        context?.let {
            db = ExchangeRateDatabase.getDatabase(it)
        }
        return context != null
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        TODO("Not yet implemented")
    }

    /* override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
         val rates = db.exchangeRateDao().getAllRates()
         val cursor = MatrixCursor(arrayOf("currency", "rate"))
         rates.forEach { cursor.addRow(arrayOf(it.currency, it.rate)) }
         return cursor
     }
 */
    override fun insert(uri: Uri, values: ContentValues?): Uri? = null
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int = 0
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int = 0
    override fun getType(uri: Uri): String? = "vnd.android.cursor.dir/vnd.com.example.marsphotos.database.ExchangeRate.exchange_rates"
}
