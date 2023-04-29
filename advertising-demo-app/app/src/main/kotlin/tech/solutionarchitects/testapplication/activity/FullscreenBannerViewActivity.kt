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
import tech.solutionarchitects.advertisingsdk.api.CloseButtonType
import tech.solutionarchitects.advertisingsdk.api.common.BannerCloseButtonClick
import tech.solutionarchitects.advertisingsdk.api.common.BannerLoadContentFail
import tech.solutionarchitects.advertisingsdk.api.common.BannerLoadContentSuccess
import tech.solutionarchitects.advertisingsdk.api.common.BannerLoadDataFail
import tech.solutionarchitects.advertisingsdk.api.common.BannerLoadDataSuccess
import tech.solutionarchitects.advertisingsdk.api.common.Size
import tech.solutionarchitects.advertisingsdk.api.feature.banner.BannerAdvertisementQuery
import tech.solutionarchitects.testapplication.databinding.ActivityFullscreenBannerViewBinding

class FullscreenBannerViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullscreenBannerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullscreenBannerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        bannerViewLayoutTest()
    }

    private fun bannerViewLayoutTest() {
        binding.bannerView.load(
            query = BannerAdvertisementQuery(
                placementId = "TestBanner",
                sizes = listOf(Size(width = 400, height = 156)),
                floorPrice = 1.234,
                currency = "SOME_CURRENCY",
                customParams = mapOf("someKey" to "someValue")
            ),
            closeButtonType = CloseButtonType.Appearing(5),
            refresh = 10
        ) { event ->
            when (event) {
                is BannerLoadDataSuccess -> {
                    println("BannerLoadDataSuccess: ${event.placementId}")
                }
                is BannerLoadDataFail -> {
                    println("BannerLoadDataFail: ${event.throwable}")
                }
                is BannerLoadContentSuccess -> {
                    println("BannerLoadContentSuccess: ${event.placementId}")
                }
                is BannerLoadContentFail -> {
                    println("BannerLoadContentFail: ${event.throwable}")
                }
                is BannerCloseButtonClick -> {
                    println("BannerCloseButtonClick: ${event.placementId}")
                    finish()
                }
                else -> {}
            }
        }
    }
}