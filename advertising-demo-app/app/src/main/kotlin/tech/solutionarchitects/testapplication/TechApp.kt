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
import tech.solutionarchitects.advertisingsdk.TechAdvertising
import tech.solutionarchitects.advertisingsdk.remoteconfig.CoreDestination
import tech.solutionarchitects.advertisingsdk.remoteconfig.InitConfig
import timber.log.Timber

/**
 * Created by Maxim Firsov on 21.08.2022.
 * firsoffmaxim@gmail.com
 */
class TechApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        TechAdvertising.init(
                context = applicationContext,
                storeUrl = "",
                uid = "uid@google.com",
                initConfig = InitConfig(
                        core = CoreDestination(
                                bannerUrl = "https://<YOUR_ADVERTISING_ENDPOINT>/",
                                nativeBannerUrl = "https://<YOUR_ADVERTISING_ENDPOINT>/",
                                videoUrl = "https://<YOUR_VIDEO_ENDPOINT>/"
                        )
                ),
                headers = mapOf(
                        "Authorization" to { "Bearer YOUR_AUTH_TOKEN" },
                ),
                debugMode = true,
        )

        Thread.setDefaultUncaughtExceptionHandler { t, e -> Timber.e(e) }
    }
}

