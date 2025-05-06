package com.youxiang8727.streamlet.data.source

import androidx.room.TypeConverter
import com.youxiang8727.streamlet.data.model.Category
import com.youxiang8727.streamlet.data.model.TransactionType
import kotlinx.serialization.json.Json
import java.time.LocalDate

class Converters {
    @TypeConverter
    fun fromTransactionType(transactionType: TransactionType): String = Json.encodeToString(transactionType)

    @TypeConverter
    fun toTransactionType(value: String): TransactionType = Json.decodeFromString(value)

    @TypeConverter
    fun fromCategory(category: Category): String = Json.encodeToString(category)

    @TypeConverter
    fun toCategory(value: String): Category = Json.decodeFromString(value)

    @TypeConverter
    fun fromDate(date: LocalDate): String = date.toString()

    @TypeConverter
    fun toDate(value: String): LocalDate = LocalDate.parse(value)
}