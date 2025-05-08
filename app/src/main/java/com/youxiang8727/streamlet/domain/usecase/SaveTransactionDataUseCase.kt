package com.youxiang8727.streamlet.domain.usecase

import com.youxiang8727.streamlet.domain.model.TransactionData
import com.youxiang8727.streamlet.domain.model.toEntity
import com.youxiang8727.streamlet.domain.repository.TransactionDataEntityRepository
import javax.inject.Inject

data class SaveTransactionDataUseCase @Inject constructor(
    private val transactionRepository: TransactionDataEntityRepository
) {
    suspend operator fun invoke(
        transactionData: TransactionData
    ) {
        transactionRepository.insert(transactionData.toEntity())
    }
}
