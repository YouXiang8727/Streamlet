package com.youxiang8727.streamlet.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.youxiang8727.streamlet.data.model.Category
import com.youxiang8727.streamlet.data.model.TransactionType

@Dao
interface CategoryDao {
    @Insert
    suspend fun insert(category: Category)

    @Insert
    suspend fun insert(categories: List<Category>)

    @Query("SELECT * FROM category WHERE transaction_type = :transactionType")
    suspend fun getCategories(transactionType: TransactionType): List<Category>

    @Query("SELECT COUNT(*) FROM category")
    suspend fun getCount(): Int
}