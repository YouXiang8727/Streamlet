package com.youxiang8727.streamlet.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.youxiang8727.streamlet.data.converter.Converters
import com.youxiang8727.streamlet.data.dao.CategoryDao
import com.youxiang8727.streamlet.data.dao.TransactionDataDao
import com.youxiang8727.streamlet.data.entity.CategoryEntity
import com.youxiang8727.streamlet.data.entity.TransactionDataEntity

@Database(entities = [TransactionDataEntity::class, CategoryEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class StreamletDatabase: RoomDatabase() {
    abstract val transactionDataDao: TransactionDataDao

    abstract val categoryDao: CategoryDao
}