package s.yarlykov.media.utils

import android.content.Context
import android.graphics.Bitmap
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap

/**
 * Drawable to Bitmap
 */
fun Context.drawableToBitmap(drawableId: Int): Bitmap? =
    ContextCompat.getDrawable(this, drawableId)?.run {
        toBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
    }