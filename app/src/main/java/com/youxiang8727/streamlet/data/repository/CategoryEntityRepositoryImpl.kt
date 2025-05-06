package com.youxiang8727.streamlet.data.repository

import com.youxiang8727.streamlet.data.model.Category
import com.youxiang8727.streamlet.data.model.TransactionType
import com.youxiang8727.streamlet.data.source.CategoryDao
import com.youxiang8727.streamlet.domain.repository.CategoryEntityRepository

data class CategoryEntityRepositoryImpl(
    private val categoryDao: CategoryDao
): CategoryEntityRepository {
    override suspend fun insert(category: Category) {
        categoryDao.insert(category)
    }

    override suspend fun insert(categories: List<Category>) {
        categoryDao.insert(categories)
    }

    override suspend fun getCount(): Int {
        return categoryDao.getCount()
    }

    override suspend fun getCategories(transactionType: TransactionType): List<Category> {
        return categoryDao.getCategories(transactionType)
    }
}