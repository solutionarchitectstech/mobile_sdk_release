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
package tech.solutionarchitects.testapplication.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tech.solutionarchitects.advertisingsdk.api.AdvertisingSDKExperimental
import tech.solutionarchitects.advertisingsdk.api.common.PlayerCloseButtonClick
import tech.solutionarchitects.advertisingsdk.api.common.PlayerLoadContentFail
import tech.solutionarchitects.advertisingsdk.api.common.PlayerLoadContentSuccess
import tech.solutionarchitects.advertisingsdk.api.common.PlayerLoadDataFail
import tech.solutionarchitects.advertisingsdk.api.common.PlayerLoadDataSuccess
import tech.solutionarchitects.advertisingsdk.api.feature.video.VastVideoView
import tech.solutionarchitects.testapplication.databinding.ActivityVastVideoBinding
import tech.solutionarchitects.testapplication.utils.getAppUsableScreenSize
import timber.log.Timber

@OptIn(AdvertisingSDKExperimental::class)
class VastVideoActivity : AppCompatActivity() {

    lateinit var binding: ActivityVastVideoBinding
    private lateinit var playerController: VastVideoView.PlayerController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVastVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val screenSize = this.getAppUsableScreenSize()

        playerController =
            VastVideoView.PlayerController.Builder(binding.vastVideoPlayer).skipTime(2)
                .draggable(screenSize.x, screenSize.y).fullScreenMode()
                .setAdvertisementListener { event ->
                    when (event) {
                        is PlayerCloseButtonClick -> Timber.d("PlayerCloseButtonClick")
                        is PlayerLoadContentFail -> {
                            Timber.d("PlayerLoadContentFail: ${event.throwable?.message}")
                        }

                        is PlayerLoadContentSuccess -> Timber.d("PlayerLoadContentSuccess")
                        is PlayerLoadDataFail -> {
                            Timber.d("PlayerLoadDataFail: ${event.throwable?.message}")
                        }

                        is PlayerLoadDataSuccess -> Timber.d("PlayerLoadDataSuccess")
                        else -> {}
                    }
                }.build()

        playerController.loadSSP()
        //playerController.launchTestResponse()
    }
}
