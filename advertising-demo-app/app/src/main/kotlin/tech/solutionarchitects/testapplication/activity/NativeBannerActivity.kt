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
import tech.solutionarchitects.advertisingsdk.api.feature.nativebanner.NativeAdvertisementQuery
import tech.solutionarchitects.testapplication.databinding.ActivityNativeBannerBinding
import timber.log.Timber

class NativeBannerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNativeBannerBinding
    private val placementId = "TestBanner"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNativeBannerBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        binding.nativeBannerView.load(
            query = NativeAdvertisementQuery(
                placementId = placementId,
                //floorPrice = 2f,
                //currency = "RUB",
                //customParams = mapOf("example" to "value", "example2" to "value2")
            ),
            refresh = 10,
            closeButtonType = CloseButtonType.Countdown(timeout = 3),
        ) { event ->
            Timber.d(event.toString())
        }
    }
}