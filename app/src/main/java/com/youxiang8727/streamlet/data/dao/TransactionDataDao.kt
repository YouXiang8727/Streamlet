package com.youxiang8727.streamlet.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.youxiang8727.streamlet.data.entity.TransactionDataEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface TransactionDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(transactionDataEntity: TransactionDataEntity)

    @Query("SELECT * FROM transaction_data WHERE date = :localDate")
    fun getTransactionDataByDate(localDate: LocalDate): Flow<List<TransactionDataEntity>>
}