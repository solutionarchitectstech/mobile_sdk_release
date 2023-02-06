package tech.solutionarchitects.testapplication.util

import android.content.Context
import android.graphics.Point
import android.view.Display
import android.view.WindowManager

fun Context.getAppUsableScreenSize(): Point {
    val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display: Display = windowManager.defaultDisplay
    val size = Point()
    display.getSize(size)
    return size
}