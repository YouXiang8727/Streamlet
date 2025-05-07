package com.youxiang8727.streamlet.domain.usecase

import com.youxiang8727.streamlet.R
import android.content.Context
import com.youxiang8727.streamlet.data.model.CategoryEntity
import com.youxiang8727.streamlet.data.model.TransactionType
import com.youxiang8727.streamlet.domain.repository.CategoryEntityRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class InitCategoriesUseCase @Inject constructor(
    private val categoryEntityRepository: CategoryEntityRepository,
    @ApplicationContext private val context: Context
) {
    suspend operator fun invoke() {
        withContext(Dispatchers.IO) {
            if (categoryEntityRepository.getCount() == 0) {
                categoryEntityRepository.insert(getInitCategories())
            }
        }
    }

    private fun getInitCategories(): List<CategoryEntity> = listOf<CategoryEntity>(
        CategoryEntity(resourceId = R.string.category_expense_food, transactionType = TransactionType.EXPENSE),
        CategoryEntity(resourceId = R.string.category_expense_transport, transactionType = TransactionType.EXPENSE),
        CategoryEntity(resourceId = R.string.category_expense_entertainment, transactionType = TransactionType.EXPENSE),
        CategoryEntity(resourceId = R.string.category_expense_utilities, transactionType = TransactionType.EXPENSE),
        CategoryEntity(resourceId = R.string.category_expense_shopping, transactionType = TransactionType.EXPENSE),
        CategoryEntity(resourceId = R.string.category_expense_education, transactionType = TransactionType.EXPENSE),
        CategoryEntity(resourceId = R.string.category_expense_communication, transactionType = TransactionType.EXPENSE),
        CategoryEntity(resourceId = R.string.category_expense_other, transactionType = TransactionType.EXPENSE),

        CategoryEntity(resourceId = R.string.category_income_salary, transactionType = TransactionType.INCOME),
        CategoryEntity(resourceId = R.string.category_income_bonus, transactionType = TransactionType.INCOME),
        CategoryEntity(resourceId = R.string.category_income_investment, transactionType = TransactionType.INCOME),
        CategoryEntity(resourceId = R.string.category_income_other, transactionType = TransactionType.INCOME)
    )
}
