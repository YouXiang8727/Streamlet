package com.youxiang8727.streamlet.domain.model

import com.youxiang8727.streamlet.data.model.TransactionDataEntity
import com.youxiang8727.streamlet.data.model.TransactionType
import java.time.LocalDate

data class TransactionData(
    val date: LocalDate,
    val transactionType: TransactionType,
    val title: String,
    val price: Int,
    val category: Category,
    val detail: String
)

fun TransactionData.toEntity(): TransactionDataEntity = TransactionDataEntity(
    date = date,
    transactionType = transactionType,
    title = title,
    price = price,
    categoryEntity = category.toEntity(),
)
