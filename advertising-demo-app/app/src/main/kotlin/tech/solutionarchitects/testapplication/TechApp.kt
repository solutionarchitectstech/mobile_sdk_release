/*
 * Copyright © Gusev Andrew, Emelianov Andrew, Spinov Dmitry [collectively referred as the Authors], 2017 - All Rights Reserved
 * [NOTICE: All information contained herein is, and remains the property of the Authors.](notice: All information contained herein is, and remains the property of the Authors.)
 * The intellectual and technical concepts contained herein are proprietary to the Authors
 * and may be covered by any existing patents of any country in the world, patents in
 * process, and are protected by trade secret or copyright law.
 *
 * Dissemination of this information or reproduction of this material is strictly forbidden unless prior
 * written permission is obtained from the Authors. Access to the source code contained herein is hereby forbidden
 * to anyone except persons (natural person, corporate or unincorporated body, whether or not having a separate
 * legal personality, and that person’s personal representatives, and successors)
 * the Authors have granted permission and who have executed Confidentiality and Non-disclosure agreements
 * explicitly covering such access.
 *
 * The copyright notice above does not provide evidence of any actual or intended publication or disclosure
 * of this source code, which in
 */

package tech.solutionarchitects.testapplication

import android.app.Application
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import tech.solutionarchitects.advertisingsdk.TechAdvertising
import tech.solutionarchitects.advertisingsdk.creative.presentation.CreativeView
import tech.solutionarchitects.advertisingsdk.remoteconfig.domain.model.CoreDestination
import tech.solutionarchitects.advertisingsdk.remoteconfig.domain.model.InitConfig
import timber.log.Timber

fun log(priority: Int, message: String) {
    Timber.log(priority, message)
    Toast.makeText(TechApp.instance.applicationContext, message, Toast.LENGTH_SHORT).show()
}

fun showMessage(msg: String, textView: TextView, creativeView: CreativeView, color: Int) {
    hideMessage(textView)

    textView.text = msg
    textView.setTextColor(color)
    textView.gravity = Gravity.CENTER
    textView.layoutParams = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )
    creativeView.addView(textView)
    // Here is the trick to store visibility state of creativeView
    // We temporarily store it in the `textView.tag` to restore it later
    // in the `hideMessage()` (see below).
    textView.tag = creativeView.visibility
    creativeView.visibility = View.VISIBLE
}

fun hideMessage(textView: TextView) {
    textView.text = ""
    textView.setTextColor(Color.BLACK)

    val parent = (textView.parent as? ViewGroup)
    parent?.apply {
        removeView(textView)
        val visibilityState = textView.tag as? Int
        visibilityState?.also { visibility = it }
    }
    textView.tag = null
}

/**
 * Created by Maxim Firsov on 21.08.2022.
 * firsoffmaxim@gmail.com
 */
class TechApp : Application() {

    companion object {
        lateinit var instance: TechApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        Timber.plant(Timber.DebugTree())

        TechAdvertising.init(
            storeUrl = "YOUR_STORE_URL",
            sessionId = "YOUR_SESSION_ID",
            initConfig = InitConfig(
                core = CoreDestination(
                    bannerUrl = "https://YOUR_BANNER_CREATIVE_ENDPOINT",
                    productUrl = "https://YOUR_PRODUCT_CREATIVE_ENDPOINT",
                ),
            ),
            headers = mapOf(
                "Authorization" to { "Bearer YOUR_AUTH_TOKEN" },
                "User-Agent" to { "YOUR_CUSTOM_USER_AGENT" }
            ),
            debugMode = true,
        )
        TechAdvertising.uid = "USER_ID"
    }
}

