package com.youxiang8727.streamlet.domain.model

import com.youxiang8727.streamlet.data.model.CategoryEntity
import com.youxiang8727.streamlet.data.model.TransactionType

data class Category(
    val name: String,
    val transactionType: TransactionType
)

fun Category.toCategoryEntity(): CategoryEntity = CategoryEntity(
    name = name,
    transactionType = transactionType
)
