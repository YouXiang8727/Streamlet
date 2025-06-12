package com.youxiang8727.streamlet.ui.screen.transaction

import android.app.DatePickerDialog
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.youxiang8727.streamlet.R
import com.youxiang8727.streamlet.data.model.TransactionType
import com.youxiang8727.streamlet.domain.model.Category
import com.youxiang8727.streamlet.domain.model.TransactionData
import com.youxiang8727.streamlet.ui.components.imagepicker.ImagePicker
import com.youxiang8727.streamlet.ui.navigation.LocalNavigationController
import com.youxiang8727.streamlet.ui.navigation.LocalSnackBarHostState
import com.youxiang8727.streamlet.ui.navigation.LocalSnackBarScope
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionScreen(
    modifier: Modifier = Modifier,
    transactionData: TransactionData? = null
) {
    val viewModel: TransactionScreenViewModel = hiltViewModel<TransactionScreenViewModel, TransactionScreenViewModel.Factory> { factory: TransactionScreenViewModel.Factory ->
        factory.create(transactionData)
    }
    val state = viewModel.uiStateFlow.collectAsStateWithLifecycle().value

    val context = LocalContext.current

    val localSnackBarScope = LocalSnackBarScope.current
    val localSnackBarHostState = LocalSnackBarHostState.current

    val localNavigationController = LocalNavigationController.current

    LaunchedEffect(state.message) {
        state.message?.let {
            localSnackBarScope.launch {
                localSnackBarHostState.showSnackbar(message = it)
            }
        }
    }

    val bottomSheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Hidden,
        skipHiddenState = false
    )
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )
    val bottomSheetScope = rememberCoroutineScope()

    BottomSheetScaffold(
        sheetContent = {
            ImagePicker(
                maxSize = 5,
                currentImages = state.images.toList(),
                dismiss = {
                    bottomSheetScope.launch {
                        bottomSheetState.hide()
                    }
                }
            ) {
                bottomSheetScope.launch {
                    bottomSheetState.hide()
                }
                viewModel.onImagesPicked(it)
            }
        },
        scaffoldState = bottomSheetScaffoldState
    ) {
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
                        label = context.getString(transactionType.stringResourceId),
                        selected = transactionType == state.transactionType
                    ) {
                        viewModel.onTransactionTypeChanged(transactionType)
                    }
                }
            }

            // 日期
            Text(
                text = "${context.getString(R.string.record_date)}：${state.date}",
                style = MaterialTheme.typography.bodyLarge
            )
            Button(onClick = {
                DatePickerDialog(
                    context,
                    { _, year, month, day ->
                        viewModel.onDateChanged(LocalDate.of(year, month + 1, day))
                    },
                    state.date.year,
                    state.date.monthValue - 1,
                    state.date.dayOfMonth
                ).show()
            }) {
                Text(context.getString(R.string.select_date))
            }

            // 名稱
            OutlinedTextField(
                value = state.title,
                onValueChange = { title ->
                    viewModel.onTitleChanged(title)
                },
                label = {
                    Text(context.getString(R.string.title))
                },
                supportingText = {
                    Text(state.titleSupportText)
                },
                modifier = Modifier.fillMaxWidth()
            )

            // 金額
            OutlinedTextField(
                value = state.amount.toString(),
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
                categories = state.categories,
                selected = state.categoryEntity,
                onSelected = { category ->
                    viewModel.onCategoryChanged(category)
                }
            )

            // 說明
            OutlinedTextField(
                value = state.note,
                onValueChange = { note ->
                    viewModel.onNoteChanged(note)
                },
                label = {
                    Text(context.getString(R.string.note_optional))
                },
                supportingText = {
                    Text(state.noteSupportText)
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            bottomSheetScope.launch {
                                bottomSheetState.expand()
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.outline_image),
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3f)
            ) {
                items(state.images) { uri ->
                    ImageListItem(uri) {
                        viewModel.deleteImage(it)
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = {
                        localNavigationController.popBackStack()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(context.getString(R.string.cancel))
                }

                Button(
                    onClick = {
                        viewModel.save()
                    },
                    enabled = state.saveable,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(context.getString(R.string.save))
                }
            }
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
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth()
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageListItem(
    uri: Uri,
    onDelete: (Uri) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .aspectRatio(1f),
        contentAlignment = Alignment.TopEnd
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            GlideImage(
                model = uri,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        IconButton(
            onClick = {
                onDelete(uri)
            },
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .padding(0.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Icon(
                imageVector = Icons.Rounded.Clear,
                contentDescription = null
            )
        }
    }
}

