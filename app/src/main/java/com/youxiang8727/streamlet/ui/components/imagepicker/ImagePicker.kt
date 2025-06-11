package com.youxiang8727.streamlet.ui.components.imagepicker

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.content.ContentUris
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.youxiang8727.streamlet.R
import com.youxiang8727.streamlet.ui.navigation.LocalSnackBarHostState
import com.youxiang8727.streamlet.ui.navigation.LocalSnackBarScope
import kotlinx.coroutines.launch

@Composable
fun ImagePicker(
    maxSize: Int = 5,
    currentImages: List<Uri> = emptyList(),
    dismiss: () -> Unit = {},
    callback: (List<Uri>) -> Unit
) {
    val context = LocalContext.current

    val snackBarHostState = LocalSnackBarHostState.current
    val snackBarScope = LocalSnackBarScope.current

    val pickedImages = remember {
        mutableStateListOf<Uri>()
    }

    val imageUris = remember {
        mutableStateListOf<Uri>()
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        val contentUri = Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)

        context.contentResolver.query(
            contentUri,
            arrayOf(Images.Media._ID),
            null,  // selection
            null,  // selectionArgs
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(Images.Media._ID)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val uri = ContentUris.withAppendedId(contentUri, id)
                imageUris.add(uri)
            }
        }
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            permissionLauncher.launch(READ_MEDIA_IMAGES)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(READ_MEDIA_IMAGES)
        } else {
            permissionLauncher.launch(READ_EXTERNAL_STORAGE)
        }
    }

    LaunchedEffect(currentImages) {
        pickedImages.clear()
        pickedImages.addAll(currentImages)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    dismiss()
                }
            ) {
                Text(
                    context.getString(R.string.cancel)
                )
            }

            Button(
                onClick = {
                    callback(pickedImages)
                }
            ) {
                Text(
                    context.getString(R.string.add)
                )
            }
        }

        LazyVerticalGrid(
            GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth()
                .weight(1f)
        ) {
            items(imageUris) { uri ->
                ImagePickerItem(
                    uri = uri,
                    index = pickedImages.indexOf(uri)
                ) {
                    if (pickedImages.contains(uri)) {
                        pickedImages.remove(uri)
                    } else if (pickedImages.size < maxSize) {
                        pickedImages.add(uri)
                    } else {
                        snackBarScope.launch {
                            snackBarHostState.showSnackbar(
                                String.format(
                                    context.getString(R.string.maximum_of_images_be_selected),
                                    maxSize
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ImagePickerItem(
    uri: Uri,
    index: Int,
    callback: (Uri) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .aspectRatio(1f)
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
            .clickable {
                callback(uri)
            },
        contentAlignment = Alignment.TopStart
    ) {
        GlideImage(
            model = uri,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Text(
            modifier = Modifier
                .fillMaxWidth(.2f)
                .aspectRatio(1f)
                .clip(CircleShape)
                .border(1.dp, MaterialTheme.colorScheme.background, CircleShape)
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = .5f)),
            text = if (index == -1) "" else (index + 1).toString(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.background,
        )
    }
}