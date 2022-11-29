package net.azarquiel.darksky.model

import android.graphics.Color
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

object util {
    fun timeStampToDate(ts: Long): String {
        val df = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return df.format(ts * 1000)
    }

    fun farToCel(far: Float): Double {
        var cel = (far - 32) * 0.5556
        val simbolos = DecimalFormatSymbols.getInstance(Locale.ENGLISH)
        val df = DecimalFormat("#0.00", simbolos)
        cel = java.lang.Double.valueOf(df.format(cel))
        return cel
    }



}