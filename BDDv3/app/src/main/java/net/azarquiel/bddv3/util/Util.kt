package net.azarquiel.bddv3.util

import android.graphics.Color

object Util {
    fun getTransparentColor(color: Int): Int {
        var alpha = 64
        val red: Int = Color.red(color)
        val green: Int = Color.green(color)
        val blue: Int = Color.blue(color)

        return Color.argb(alpha, red, green, blue)
    }
}