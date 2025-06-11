package com.youxiang8727.streamlet.domain.usecase

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import java.security.MessageDigest
import javax.inject.Inject

data class SaveImageUseCase @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    operator fun invoke(
        uri: Uri
    ): String {
        try {
//            val fileName = calculateImageHash(uri)
//
//            val imageFile = File(context.filesDir, "$fileName.jpg")
//
//            if (imageFile.exists()) {
//                return imageFile.absolutePath // 圖片已存在，回傳路徑
//            }
//
//            // 圖片不存在，進行儲存
//            context.contentResolver.openInputStream(uri)?.use { inputStream ->
//                FileOutputStream(imageFile).use { outputStream ->
//                    inputStream.copyTo(outputStream)
//                }
//            } ?: throw Exception("Failed to open input stream from Uri")
//
//            return imageFile.absolutePath // 回傳新儲存圖片的路徑
            return uri.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    private fun calculateImageHash(uri: Uri): String {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: throw Exception("Failed to open input stream from Uri")
            val digest = MessageDigest.getInstance("SHA-256")

            inputStream.use { stream ->
                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (stream.read(buffer).also { bytesRead = it } != -1) {
                    digest.update(buffer, 0, bytesRead)
                }
            }

            // 轉成 16 進位字串
            digest.digest().joinToString("") { "%02x".format(it) }
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}