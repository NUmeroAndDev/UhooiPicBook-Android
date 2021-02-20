package com.theuhooi.uhooipicbook.extensions

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import java.io.File
import java.io.FileOutputStream

fun Drawable.saveAsPngFile(file: File) {
    FileOutputStream(file).use { outputStream ->
        val bitmap = (this as BitmapDrawable).bitmap
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
    }
}