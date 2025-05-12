package com.youxiang8727.streamlet.data.model

import androidx.compose.ui.graphics.Color
import com.youxiang8727.streamlet.R
import com.youxiang8727.streamlet.data.serializer.ColorSerializer
import kotlinx.serialization.Serializable

@Serializable
enum class TransactionType(
    val stringResourceId: Int,
    @Serializable(with = ColorSerializer::class)
    val color: Color
) {
    EXPENSE(R.string.transaction_type_expense, Color(0xFFF44336)),
    INCOME(R.string.transaction_type_income, Color(0xFF4CAF50))
}
