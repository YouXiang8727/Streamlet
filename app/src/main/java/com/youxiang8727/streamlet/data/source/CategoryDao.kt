package com.youxiang8727.streamlet.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.youxiang8727.streamlet.data.model.CategoryEntity
import com.youxiang8727.streamlet.data.model.TransactionType

@Dao
interface CategoryDao {
    @Insert
    suspend fun insert(categoryEntity: CategoryEntity)

    @Insert
    suspend fun insert(categories: List<CategoryEntity>)

    @Query("SELECT * FROM category WHERE transaction_type = :transactionType")
    suspend fun getCategories(transactionType: TransactionType): List<CategoryEntity>

    @Query("SELECT COUNT(*) FROM category")
    suspend fun getCount(): Int
}