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
import tech.solutionarchitects.advertisingsdk.api.common.Size
import tech.solutionarchitects.advertisingsdk.api.feature.banner.BannerAdvertisementQuery
import tech.solutionarchitects.testapplication.databinding.ActivityBannerViewBinding
import tech.solutionarchitects.testapplication.utils.showDebugToast

class BannerViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBannerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBannerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bannerViewLayoutTest()
    }


    private fun bannerViewLayoutTest() {
        binding.bannerView.load(
            query = BannerAdvertisementQuery(
                placementId = "1",
                sizes = listOf(Size(width = 1024, height = 768)),
                floorPrice = 2.0,
                currency = "RUB",
                customParams = mapOf("example" to "value", "example2" to "value2")
            ),
            refresh = 30,
            closeButtonType = CloseButtonType.Countdown(15),
        ) { event ->
            showDebugToast(event)
        }
    }
}
