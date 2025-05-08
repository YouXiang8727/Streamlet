package com.youxiang8727.streamlet.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.youxiang8727.streamlet.data.model.TransactionDataEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface TransactionDataDao {
    @Insert
    suspend fun insert(transactionDataEntity: TransactionDataEntity)

    @Query("SELECT * FROM transaction_data WHERE date = :localDate")
    fun getTransactionDataByDate(localDate: LocalDate): Flow<List<TransactionDataEntity>>
}