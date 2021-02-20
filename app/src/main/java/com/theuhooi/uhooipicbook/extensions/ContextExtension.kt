package com.theuhooi.uhooipicbook.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

fun Context.createTempPngFileUri(drawable: Drawable): Uri? {
    val cacheFile = File(externalCacheDir, "share_temp.png")
    drawable.saveAsPngFile(cacheFile)
    return FileProvider.getUriForFile(this, "$packageName.fileprovider", cacheFile)
}