package com.youxiang8727.streamlet.ui.screen.transaction

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.youxiang8727.streamlet.data.model.TransactionType
import java.time.LocalDate
import com.youxiang8727.streamlet.R
import com.youxiang8727.streamlet.domain.model.Category
import com.youxiang8727.streamlet.ui.navigation.LocalSnackBarHostState
import com.youxiang8727.streamlet.ui.navigation.LocalSnackBarScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: TransactionScreenViewModel = hiltViewModel()

    val context = LocalContext.current

    val localSnackBarScope = LocalSnackBarScope.current
    val localSnackBarHostState = LocalSnackBarHostState.current

    LaunchedEffect(viewModel.state.message) {
        viewModel.state.message?.let {
            localSnackBarScope.launch {
                localSnackBarHostState.showSnackbar(message = it)
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 類型選擇器
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TransactionType.entries.forEach { transactionType ->
                TypeChip(
                    label = context.getString(transactionType.id),
                    selected = transactionType == viewModel.state.transactionType
                ) {
                    viewModel.onTransactionTypeChanged(transactionType)
                }
            }
        }

        // 日期
        Text(text = "${context.getString(R.string.record_date)}：${viewModel.state.date}", style = MaterialTheme.typography.bodyLarge)
        Button(onClick = {
            DatePickerDialog(
                context,
                { _, year, month, day ->
                    viewModel.onDateChanged(LocalDate.of(year, month + 1, day))
                },
                viewModel.state.date.year,
                viewModel.state.date.monthValue - 1,
                viewModel.state.date.dayOfMonth
            ).show()
        }) {
            Text(context.getString(R.string.select_date))
        }

        // 名稱
        OutlinedTextField(
            value = viewModel.state.title,
            onValueChange = { title ->
                viewModel.onTitleChanged(title)
            },
            label = {
                Text(context.getString(R.string.title))
            },
            supportingText = {
                Text(viewModel.state.titleSupportText)
            },
            modifier = Modifier.fillMaxWidth()
        )

        // 金額
        OutlinedTextField(
            value = viewModel.state.amount.toString(),
            onValueChange = {
                val amount = it.filter { c -> c.isDigit() }.toIntOrNull() ?: 0
                viewModel.onAmountChanged(amount)
            },
            label = { Text(context.getString(R.string.amount)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // 種類選擇
        CategoryDropdown(
            categories = viewModel.state.categories,
            selected = viewModel.state.categoryEntity,
            onSelected = { category ->
                viewModel.onCategoryChanged(category)
            }
        )

        // 說明
        OutlinedTextField(
            value = viewModel.state.note,
            onValueChange = { note ->
                viewModel.onNoteChanged(note)
            },
            label = {
                Text(context.getString(R.string.note_optional))
            },
            supportingText = {
                Text(viewModel.state.noteSupportText)
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 儲存按鈕
        Button(
            onClick = {
                viewModel.save()
            },
            enabled = viewModel.state.saveable,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(context.getString(R.string.save))
        }
    }
}

@Composable
fun TypeChip(label: String, selected: Boolean, onClick: () -> Unit) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(label) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(
    categories: List<Category>,
    selected: Category?,
    onSelected: (Category) -> Unit
) {
    val context = LocalContext.current

    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(
            value = selected?.title ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text(context.getString(R.string.category)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable).fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            categories.forEach { cat ->
                DropdownMenuItem(
                    text = { Text(cat.title) },
                    onClick = {
                        onSelected(cat)
                        expanded = false
                    }
                )
            }
        }
    }
}
