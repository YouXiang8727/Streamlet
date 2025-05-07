package com.youxiang8727.streamlet.data.model

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.youxiang8727.streamlet.domain.model.Category
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "resource_id")
    val resourceId: Int? = null,
    @ColumnInfo(name = "name")
    val name: String? = null,
    @ColumnInfo(name = "transaction_type")
    val transactionType: TransactionType
)

fun CategoryEntity.toCategory(context: Context): Category = Category(
    name = if (resourceId == null) name ?: "" else context.getString(resourceId),
    transactionType = transactionType
)