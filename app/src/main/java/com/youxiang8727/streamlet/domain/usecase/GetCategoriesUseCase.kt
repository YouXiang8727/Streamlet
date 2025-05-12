package com.youxiang8727.streamlet.domain.usecase

import android.content.Context
import com.youxiang8727.streamlet.data.model.TransactionType
import com.youxiang8727.streamlet.data.entity.toCategory
import com.youxiang8727.streamlet.domain.model.Category
import com.youxiang8727.streamlet.domain.repository.CategoryEntityRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class GetCategoriesUseCase @Inject constructor(
    private val categoryEntityRepository: CategoryEntityRepository,
    @ApplicationContext private val context: Context
) {
    suspend operator fun invoke(transactionType: TransactionType): List<Category> {
        return withContext(Dispatchers.IO) {
            categoryEntityRepository.getCategories(transactionType).map {
                it.toCategory(context)
            }
        }
    }
}
