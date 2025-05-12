package com.youxiang8727.streamlet.domain.usecase

import android.content.Context
import com.youxiang8727.streamlet.data.entity.toTransactionData
import com.youxiang8727.streamlet.domain.model.TransactionData
import com.youxiang8727.streamlet.domain.repository.TransactionDataEntityRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

data class GetTransactionDataUseCase @Inject constructor(
    private val transactionDataRepository: TransactionDataEntityRepository,
    @ApplicationContext private val context: Context
) {
    operator fun invoke(date: LocalDate): Flow<List<TransactionData>> {
        return transactionDataRepository.getTransactionByDate(date).map { list ->
            list.map { transactionDateEntity ->
                transactionDateEntity.toTransactionData(context)
            }
        }
    }
}
