package com.youxiang8727.streamlet.domain.model

import com.youxiang8727.streamlet.data.model.TransactionDataEntity
import com.youxiang8727.streamlet.data.model.TransactionType
import java.time.LocalDate

data class TransactionData(
    val date: LocalDate,
    val transactionType: TransactionType,
    val title: String,
    val amount: Int,
    val category: Category,
    val note: String
)

fun TransactionData.toEntity(): TransactionDataEntity = TransactionDataEntity(
    date = date,
    transactionType = transactionType,
    title = title,
    amount = amount,
    categoryEntity = category.toEntity(),
    note = note
)
