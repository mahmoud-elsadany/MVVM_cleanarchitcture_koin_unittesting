package com.efg.valu.sales.util.extention

import android.content.Context

fun Float.convertDpToPx(context: Context): Float {
    return this * context.resources.displayMetrics.density
}
fun Float.convertPxToDp(context: Context): Float {
    return this / context.resources.displayMetrics.density
}