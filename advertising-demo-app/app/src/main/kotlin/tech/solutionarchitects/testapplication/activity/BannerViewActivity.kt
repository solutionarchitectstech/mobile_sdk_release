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
import tech.solutionarchitects.advertisingsdk.api.common.BannerCreative
import tech.solutionarchitects.advertisingsdk.api.common.Size
import tech.solutionarchitects.advertisingsdk.api.feature.banner.BannerCreativeQuery
import tech.solutionarchitects.testapplication.databinding.ActivityBannerViewBinding
import tech.solutionarchitects.testapplication.utils.showDebugMessage

class BannerViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBannerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBannerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bannerView.query = BannerCreativeQuery(
            placementId = "YOUR_PLACEMENT_ID",
            sizes = listOf(Size(width = 260, height = 106)),
            floorPrice = 2.0,
            currency = "RUB",
            customParams = mapOf(
                "skuId" to "LG00001",
                "skuName" to "Lego bricks (speed boat)",
                "category" to "Kids",
                "subСategory" to "Lego",
                "gdprConsent" to "CPsmEWIPsmEWIABAMBFRACBsABEAAAAgEIYgACJAAYiAAA.QRXwAgAAgivA",
                "ccpa" to "1YNN",
                "coppa" to "1"
            ),
            closeButtonType = CloseButtonType.Countdown(5)
        )

        val bannerViewController = BannerCreative(
            lifecycle = lifecycle,
            banner = binding.bannerView,
            refresh = 10
        ) { event ->
            showDebugMessage(event)
        }

        bannerViewController.load()
    }

}
