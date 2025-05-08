package com.youxiang8727.streamlet.data.repository

import com.youxiang8727.streamlet.data.model.TransactionDataEntity
import com.youxiang8727.streamlet.data.source.TransactionDataDao
import com.youxiang8727.streamlet.domain.repository.TransactionDataEntityRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

data class TransactionDataEntityRepositoryImpl(
    private val transactionDataDao: TransactionDataDao
): TransactionDataEntityRepository {
    override suspend fun insert(transactionDataEntity: TransactionDataEntity) {
        transactionDataDao.insert(transactionDataEntity)
    }

    override fun getTransactionByDate(localDate: LocalDate): Flow<List<TransactionDataEntity>> {
        return transactionDataDao.getTransactionDataByDate(localDate)
    }
}
