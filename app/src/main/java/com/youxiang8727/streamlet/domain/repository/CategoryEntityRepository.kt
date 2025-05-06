package com.youxiang8727.streamlet.domain.repository

import com.youxiang8727.streamlet.data.model.Category
import com.youxiang8727.streamlet.data.model.TransactionType

interface CategoryEntityRepository {
    suspend fun insert(category: Category)

    suspend fun insert(categories: List<Category>)

    suspend fun getCount(): Int

    suspend fun getCategories(transactionType: TransactionType): List<Category>
}