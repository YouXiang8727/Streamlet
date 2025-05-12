package com.youxiang8727.streamlet.domain.model

import androidx.compose.ui.graphics.Color
import com.youxiang8727.streamlet.data.entity.CategoryEntity
import com.youxiang8727.streamlet.data.model.TransactionType

data class Category(
    val title: String,
    val transactionType: TransactionType,
    val color: Color
)

fun Category.toEntity(): CategoryEntity = CategoryEntity(
    title = title,
    transactionType = transactionType,
    color = color
)
