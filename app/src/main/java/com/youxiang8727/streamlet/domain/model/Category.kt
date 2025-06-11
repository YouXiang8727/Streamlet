package com.youxiang8727.streamlet.domain.model

import androidx.compose.ui.graphics.Color
import com.youxiang8727.streamlet.data.entity.CategoryEntity
import com.youxiang8727.streamlet.data.model.TransactionType
import com.youxiang8727.streamlet.data.serializer.ColorSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val title: String,
    val transactionType: TransactionType,
    @Contextual
    @Serializable(with = ColorSerializer::class)
    val color: Color
)

fun Category.toEntity(): CategoryEntity = CategoryEntity(
    title = title,
    transactionType = transactionType,
    color = color
)
