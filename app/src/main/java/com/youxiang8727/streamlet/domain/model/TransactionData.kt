package com.youxiang8727.streamlet.domain.model

import android.net.Uri
import androidx.core.net.toUri
import com.youxiang8727.streamlet.data.entity.TransactionDataEntity
import com.youxiang8727.streamlet.data.model.TransactionType
import com.youxiang8727.streamlet.ui.screen.transaction.TransactionUiState
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.io.File
import java.time.LocalDate

@Serializable
data class TransactionData(
    val id: Int? = null,
    @Contextual
    val date: LocalDate = LocalDate.now(),
    val transactionType: TransactionType,
    val title: String,
    val amount: Int,
    val category: Category,
    val note: String,
    val images: List<String>
)

fun TransactionData.toEntity(): TransactionDataEntity = TransactionDataEntity(
    id = id,
    date = date,
    transactionType = transactionType,
    title = title,
    amount = amount,
    categoryEntity = category.toEntity(),
    note = note,
    images = images
)

fun TransactionData.toTransactionUiState(): TransactionUiState = TransactionUiState(
    id = id,
    transactionType = transactionType,
    date = date,
    title = title,
    amount = amount,
    categoryEntity = category,
    note = note,
    images = images.map {
        it.toUri()
    }
)
