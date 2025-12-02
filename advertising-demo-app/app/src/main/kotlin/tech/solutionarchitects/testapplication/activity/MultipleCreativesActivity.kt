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

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import tech.solutionarchitects.advertisingsdk.creative.presentation.CreativeEventListener
import tech.solutionarchitects.advertisingsdk.creative.domain.model.Size
import tech.solutionarchitects.advertisingsdk.creative.presentation.Creative
import tech.solutionarchitects.advertisingsdk.creative.domain.model.CreativeQuery
import tech.solutionarchitects.advertisingsdk.creative.presentation.CreativeView
import tech.solutionarchitects.testapplication.databinding.ActivityMultipleCreativesBinding
import tech.solutionarchitects.testapplication.hideMessage
import tech.solutionarchitects.testapplication.log
import tech.solutionarchitects.testapplication.showMessage

class MultipleCreativesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMultipleCreativesBinding

    private lateinit var errorLabels: Map<Int, TextView>

    private lateinit var creative: Creative

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMultipleCreativesBinding.inflate(layoutInflater)

        binding.creativeView1.query = CreativeQuery(
            placementId = "VAST_Inline_Simple",
            sizes = listOf(Size(width = 260, height = 106)),
            floorPrice = 1.99,
            currency = "USD",
            customParams = mapOf(
                "skuId" to "LG00001",
                "skuName" to "Lego bricks (speed boat)",
                "category" to "Kids",
                "subCategory" to "Lego"
            ),
        )

        binding.creativeView2.query = CreativeQuery(
            placementId = "VAST_Wrapper_Compound",
            sizes = listOf(Size(width = 260, height = 106)),
            floorPrice = 2.84,
            currency = "USD",
        )

        binding.creativeView3.isScrollEnabled = true
        binding.creativeView3.scaleToFit = false
        binding.creativeView3.query = CreativeQuery(
            placementId = "IMAGE_BANNER",
            sizes = listOf(Size(width = 260, height = 106)),
        )

        binding.creativeView4.isScrollEnabled = true
        binding.creativeView4.scaleToFit = false
        binding.creativeView4.query = CreativeQuery(
            placementId = "HTML_BANNER",
            sizes = listOf(Size(width = 260, height = 106)),
        )

        binding.creativeView5.query = CreativeQuery(
            placementId = "MY_CREATIVE",
            sizes = listOf(Size(width = 260, height = 106)),
        )

        errorLabels = mapOf(
            Pair(binding.creativeView1.hashCode(), TextView(this)),
            Pair(binding.creativeView2.hashCode(), TextView(this)),
            Pair(binding.creativeView3.hashCode(), TextView(this)),
            Pair(binding.creativeView4.hashCode(), TextView(this)),
            Pair(binding.creativeView5.hashCode(), TextView(this)),
        )

        creative = Creative(
            lifecycle = lifecycle,
            creativeViews = listOf(
                binding.creativeView1,
                binding.creativeView2,
                binding.creativeView3,
                binding.creativeView4,
                binding.creativeView5,
            ),
            listener = object : CreativeEventListener {
                override fun onLoadDataSuccess(creativeView: CreativeView) {
                    val placementId = creativeView.query?.placementId
                    log(Log.DEBUG, "onLoadDataSuccess[${placementId}]")

                    val label = errorLabels[creativeView.hashCode()]
                    if (label != null) {
                        hideMessage(label)
                    }
                }

                override fun onLoadDataFail(creativeView: CreativeView, throwable: Throwable?) {
                    val placementId = creativeView.query?.placementId
                    val msg = "onLoadDataFail[${placementId}]: ${throwable?.message}"
                    log(Log.ERROR, msg)

                    val label = errorLabels[creativeView.hashCode()]
                    if (label != null) {
                        showMessage(msg, label, creativeView, Color.RED)
                    }
                }

                override fun onLoadContentSuccess(creativeView: CreativeView, ext: Map<String, Any>) {
                    val placementId = creativeView.query?.placementId
                    val trackingId = ext["trackingId"] as? String ?: ""
                    val creativeId = ext["creativeId"] as? String ?: ""
                    log(Log.DEBUG, "onLoadContentSuccess[placementId: ${placementId}, trackingId: ${trackingId}, creativeId: ${creativeId}]")

                    val label = errorLabels[creativeView.hashCode()]
                    if (label != null) {
                        hideMessage(label)
                    }
                }

                override fun onLoadContentFail(creativeView: CreativeView, throwable: Throwable?) {
                    val placementId = creativeView.query?.placementId
                    val msg = "onLoadContentFail[${placementId}]: ${throwable?.message}"
                    log(Log.ERROR, msg)

                    val label = errorLabels[creativeView.hashCode()]
                    if (label != null) {
                        showMessage(msg, label, creativeView, Color.RED)
                    }
                }

                override fun onNoAdContent(creativeView: CreativeView) {
                    val placementId = creativeView.query?.placementId
                    val msg = "onNoAdContent[${placementId}]"
                    log(Log.WARN, msg)

                    val label = errorLabels[creativeView.hashCode()]
                    if (label != null) {
                        showMessage(msg, label, creativeView, Color.RED)
                    }
                }

                override fun onClose(creativeView: CreativeView) {
                    val placementId = creativeView.query?.placementId
                    log(Log.DEBUG, "onClose[${placementId}]")

                    val label = errorLabels[creativeView.hashCode()]
                    if (label != null) {
                        hideMessage(label)
                    }
                }
            }
        )

        setContentView(binding.root)

        creative.load()
    }
}