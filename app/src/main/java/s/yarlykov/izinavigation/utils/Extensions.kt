package s.yarlykov.izinavigation.utils

import android.graphics.Rect
import kotlin.math.abs

/**
 * Используется для работы с прямоугольниками от ItemDecoration.
 * Там могут присутствовать отрицательные значения.
 */
val Rect.min: Int
    get() = listOf(left, top, right, bottom).filter { it != 0 }.map { abs(it) }.minOrNull() ?: 0



