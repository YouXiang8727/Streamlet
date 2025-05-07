package com.youxiang8727.streamlet.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.youxiang8727.streamlet.data.model.CategoryEntity
import com.youxiang8727.streamlet.data.model.TransactionDataEntity

@Database(entities = [TransactionDataEntity::class, CategoryEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class StreamletDatabase: RoomDatabase() {
    abstract val transactionDataDao: TransactionDataDao

    abstract val categoryDao: CategoryDao
}