package com.youxiang8727.streamlet.domain.repository

import com.youxiang8727.streamlet.data.model.TransactionDataEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TransactionDataEntityRepository {
    suspend fun insert(transactionDataEntity: TransactionDataEntity)

    fun getTransactionByDate(localDate: LocalDate): Flow<List<TransactionDataEntity>>
}