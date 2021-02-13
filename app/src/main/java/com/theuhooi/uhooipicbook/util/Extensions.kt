package com.theuhooi.uhooipicbook.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

fun Context.createTempPngFileUri(drawable: Drawable): Uri? {
    val cacheFile = File(externalCacheDir, "share_temp.png")
    drawable.saveAsPngFile(cacheFile)
    return FileProvider.getUriForFile(this, "$packageName.fileprovider", cacheFile)
}

fun Drawable.saveAsPngFile(file: File) {
    FileOutputStream(file).use { outputStream ->
        val bitmap = (this as BitmapDrawable).bitmap
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
    }
}