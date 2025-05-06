package com.youxiang8727.streamlet.data.model

import com.youxiang8727.streamlet.R
import kotlinx.serialization.Serializable

@Serializable
enum class TransactionType(val id: Int) {
    EXPENSE(R.string.transaction_type_expense),
    INCOME(R.string.transaction_type_income)
}
