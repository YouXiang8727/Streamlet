package com.youxiang8727.streamlet.data.repository

import com.youxiang8727.streamlet.data.model.CategoryEntity
import com.youxiang8727.streamlet.data.model.TransactionType
import com.youxiang8727.streamlet.data.source.CategoryDao
import com.youxiang8727.streamlet.domain.repository.CategoryEntityRepository

data class CategoryEntityRepositoryImpl(
    private val categoryDao: CategoryDao
): CategoryEntityRepository {
    override suspend fun insert(categoryEntity: CategoryEntity) {
        categoryDao.insert(categoryEntity)
    }

    override suspend fun insert(categories: List<CategoryEntity>) {
        categoryDao.insert(categories)
    }

    override suspend fun getCount(): Int {
        return categoryDao.getCount()
    }

    override suspend fun getCategories(transactionType: TransactionType): List<CategoryEntity> {
        return categoryDao.getCategories(transactionType)
    }
}