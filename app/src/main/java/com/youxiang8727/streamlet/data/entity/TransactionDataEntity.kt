package com.youxiang8727.streamlet.data.entity

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.youxiang8727.streamlet.data.model.TransactionType
import com.youxiang8727.streamlet.domain.model.TransactionData
import java.time.LocalDate

@Entity(tableName = "transaction_data")
data class TransactionDataEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,
    @ColumnInfo(name = "date")
    val date: LocalDate,
    @ColumnInfo(name = "transaction_type")
    val transactionType: TransactionType,
    @ColumnInfo(name = "amount")
    val amount: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "category")
    val categoryEntity: CategoryEntity,
    @ColumnInfo(name = "note")
    val note: String = "",
    @ColumnInfo(name = "images")
    val images: List<String> = emptyList()
)

fun TransactionDataEntity.toTransactionData(context: Context): TransactionData = TransactionData(
    id = id,
    date = date,
    transactionType = transactionType,
    amount = amount,
    title = title,
    category = categoryEntity.toCategory(context),
    note = note,
    images = images
)
