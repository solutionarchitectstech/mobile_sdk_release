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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.solutionarchitects.advertisingsdk.core.model.Size
import tech.solutionarchitects.advertisingsdk.listener.BannerCloseButtonClick
import tech.solutionarchitects.advertisingsdk.listener.BannerSentClickStatistic
import tech.solutionarchitects.advertisingsdk.listener.BannerSentLoadStatistic
import tech.solutionarchitects.advertisingsdk.listener.BannerSentViewStatistic
import tech.solutionarchitects.advertisingsdk.types.CloseButtonType
import tech.solutionarchitects.testapplication.R
import tech.solutionarchitects.testapplication.databinding.ActivityScrollableWithBannersBinding
import timber.log.Timber

class ScrollableActivityWithBanners : AppCompatActivity() {

    private lateinit var binding: ActivityScrollableWithBannersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScrollableWithBannersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val scope = CoroutineScope(Dispatchers.Main)

        // NOTE(radchenko): Click on button reproduces scenario when a UI change occurs in the host-app that overlap Banner
        binding.buttonForPlaceholder.setOnClickListener {
            binding.placeholderView.isVisible = !binding.placeholderView.isVisible
        }

        binding.nativeBannerView.load(
            placementId = "TestBanner",
            closeButtonType = CloseButtonType.Countdown(milliseconds = 3_000),
            //floorPrice = 2f,
            //currency = "RUB",
            //customParams = mapOf("example" to "value", "example2" to "value2")
        ) { event ->
            Timber.d(event.toString())
            when (event) {
                is BannerCloseButtonClick -> {
                    finish()
                }
                is BannerSentClickStatistic -> scope.launch {
                    Toast.makeText(
                        this@ScrollableActivityWithBanners.applicationContext,
                        resources.getString(R.string.banner_tapped_alert, event.placementId),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is BannerSentLoadStatistic -> scope.launch {
                    Toast.makeText(
                        this@ScrollableActivityWithBanners.applicationContext,
                        resources.getString(R.string.banner_loaded_alert, event.placementId),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is BannerSentViewStatistic -> scope.launch {
                    Toast.makeText(
                        this@ScrollableActivityWithBanners.applicationContext,
                        resources.getString(R.string.banner_viewed_alert, event.placementId),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.bannerView.load(
            placementId = "1",
            sizes = listOf(Size(width = 1024, height = 768)),
            closeButtonType = CloseButtonType.Countdown(milliseconds = 30_000),
            //customParams = mapOf("example" to "value", "example2" to "value2")
        ) { event ->
            Timber.d(event.toString())
            when (event) {
                is BannerCloseButtonClick -> {
                    finish()
                }
                is BannerSentClickStatistic -> scope.launch {
                    Toast.makeText(
                        this@ScrollableActivityWithBanners.applicationContext,
                        resources.getString(R.string.banner_tapped_alert, event.placementId),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is BannerSentLoadStatistic -> scope.launch {
                    Toast.makeText(
                        this@ScrollableActivityWithBanners.applicationContext,
                        resources.getString(R.string.banner_loaded_alert, event.placementId),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is BannerSentViewStatistic -> scope.launch {
                    Toast.makeText(
                        this@ScrollableActivityWithBanners.applicationContext,
                        resources.getString(R.string.banner_viewed_alert, event.placementId),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}