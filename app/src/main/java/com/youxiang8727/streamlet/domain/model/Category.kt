package com.youxiang8727.streamlet.domain.model

import com.youxiang8727.streamlet.data.model.CategoryEntity
import com.youxiang8727.streamlet.data.model.TransactionType

data class Category(
    val title: String,
    val transactionType: TransactionType
)

fun Category.toEntity(): CategoryEntity = CategoryEntity(
    title = title,
    transactionType = transactionType
)
