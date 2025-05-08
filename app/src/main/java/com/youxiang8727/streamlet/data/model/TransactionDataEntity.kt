package com.youxiang8727.streamlet.data.model

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.youxiang8727.streamlet.domain.model.TransactionData
import java.time.LocalDate

@Entity(tableName = "transactionData")
data class TransactionDataEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "date")
    val date: LocalDate,
    @ColumnInfo(name = "transaction_type")
    val transactionType: TransactionType,
    @ColumnInfo(name = "price")
    val price: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "category")
    val categoryEntity: CategoryEntity,
    @ColumnInfo(name = "detail")
    val detail: String = "",
)

fun TransactionDataEntity.toTransactionData(context: Context): TransactionData = TransactionData(
    date = date,
    transactionType = transactionType,
    price = price,
    title = title,
    category = categoryEntity.toCategory(context),
    detail = detail
)
