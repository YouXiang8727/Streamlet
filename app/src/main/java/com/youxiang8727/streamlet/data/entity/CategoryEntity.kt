package com.youxiang8727.streamlet.data.entity

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.youxiang8727.streamlet.data.model.TransactionType
import com.youxiang8727.streamlet.data.serializer.ColorSerializer
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
    @ColumnInfo(name = "title")
    val title: String? = null,
    @ColumnInfo(name = "transaction_type")
    val transactionType: TransactionType,
    @ColumnInfo(name = "color")
    @Serializable(with = ColorSerializer::class)
    val color: Color
)

fun CategoryEntity.toCategory(context: Context): Category = Category(
    title = if (resourceId == null) title ?: "" else context.getString(resourceId),
    transactionType = transactionType,
    color = color
)