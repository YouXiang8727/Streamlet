package com.youxiang8727.streamlet.domain.usecase

import com.youxiang8727.streamlet.data.model.CategoryEntity
import com.youxiang8727.streamlet.data.model.TransactionType
import com.youxiang8727.streamlet.domain.repository.CategoryEntityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class GetCategoriesUseCase @Inject constructor(
    private val categoryEntityRepository: CategoryEntityRepository
) {
    suspend operator fun invoke(transactionType: TransactionType): List<CategoryEntity> {
        return withContext(Dispatchers.IO) {
            categoryEntityRepository.getCategories(transactionType)
        }
    }
}
