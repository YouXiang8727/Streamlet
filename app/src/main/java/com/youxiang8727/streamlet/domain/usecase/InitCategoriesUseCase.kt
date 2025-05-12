package com.youxiang8727.streamlet.domain.usecase

import com.youxiang8727.streamlet.R
import android.content.Context
import androidx.compose.ui.graphics.Color
import com.youxiang8727.streamlet.data.entity.CategoryEntity
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

    private fun getInitCategories(): List<CategoryEntity> = listOf(
        CategoryEntity(
            resourceId = R.string.category_expense_food,
            transactionType = TransactionType.EXPENSE,
            color = Color(0xFFFF9800)
        ),
        CategoryEntity(
            resourceId = R.string.category_expense_transport,
            transactionType = TransactionType.EXPENSE,
            color = Color(0xFF9C27B0)
        ),
        CategoryEntity(
            resourceId = R.string.category_expense_entertainment,
            transactionType = TransactionType.EXPENSE,
            color = Color(0xFFE91E63)
        ),
        CategoryEntity(
            resourceId = R.string.category_expense_utilities,
            transactionType = TransactionType.EXPENSE,
            color = Color(0xFF03A9F4)
        ),
        CategoryEntity(
            resourceId = R.string.category_expense_shopping,
            transactionType = TransactionType.EXPENSE,
            color = Color(0xFFFFC107)
        ),
        CategoryEntity(
            resourceId = R.string.category_expense_education,
            transactionType = TransactionType.EXPENSE,
            color = Color(0xFF8BC34A)
        ),
        CategoryEntity(
            resourceId = R.string.category_expense_communication,
            transactionType = TransactionType.EXPENSE,
            color = Color(0xFF607D8B)
        ),
        CategoryEntity(
            resourceId = R.string.category_expense_other,
            transactionType = TransactionType.EXPENSE,
            color = Color(0xFF795548)
        ),

        CategoryEntity(
            resourceId = R.string.category_income_salary,
            transactionType = TransactionType.INCOME,
            color = Color(0xFF388E3C)
        ),
        CategoryEntity(
            resourceId = R.string.category_income_bonus,
            transactionType = TransactionType.INCOME,
            color = Color(0xFF00BCD4)
        ),
        CategoryEntity(
            resourceId = R.string.category_income_investment,
            transactionType = TransactionType.INCOME,
            color = Color(0xFF009688)
        ),
        CategoryEntity(
            resourceId = R.string.category_income_other,
            transactionType = TransactionType.INCOME,
            color = Color(0xFFCDDC39)
        )
    )
}
