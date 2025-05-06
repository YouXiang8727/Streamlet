package com.youxiang8727.streamlet.data.di

import android.content.Context
import androidx.room.Room
import com.youxiang8727.streamlet.data.repository.CategoryEntityRepositoryImpl
import com.youxiang8727.streamlet.data.source.TransactionDataDao
import com.youxiang8727.streamlet.data.source.StreamletDatabase
import com.youxiang8727.streamlet.data.source.CategoryDao
import com.youxiang8727.streamlet.domain.repository.CategoryEntityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): StreamletDatabase {
        return Room.databaseBuilder(
            context,
            StreamletDatabase::class.java,
            "streamlet.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTransactionDataDao(streamletDatabase: StreamletDatabase): TransactionDataDao {
        return streamletDatabase.transactionDataDao
    }

    @Provides
    @Singleton
    fun provideCategoryDao(streamletDatabase: StreamletDatabase): CategoryDao {
        return streamletDatabase.categoryDao
    }

    @Provides
    @Singleton
    fun provideCategoryEntityRepository(categoryDao: CategoryDao): CategoryEntityRepository {
        return CategoryEntityRepositoryImpl(categoryDao)
    }
}