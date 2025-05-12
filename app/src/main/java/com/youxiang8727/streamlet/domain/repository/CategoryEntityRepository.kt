package com.youxiang8727.streamlet.domain.repository

import com.youxiang8727.streamlet.data.entity.CategoryEntity
import com.youxiang8727.streamlet.data.model.TransactionType

interface CategoryEntityRepository {
    suspend fun insert(categoryEntity: CategoryEntity)

    suspend fun insert(categories: List<CategoryEntity>)

    suspend fun getCount(): Int

    suspend fun getCategories(transactionType: TransactionType): List<CategoryEntity>
}