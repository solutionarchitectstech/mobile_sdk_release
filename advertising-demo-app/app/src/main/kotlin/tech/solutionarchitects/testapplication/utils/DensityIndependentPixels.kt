package tech.solutionarchitects.testapplication.utils

import android.content.Context


fun Int.toDP(context: Context): Int {
    return (this * context.resources.displayMetrics.density).toInt()
}
