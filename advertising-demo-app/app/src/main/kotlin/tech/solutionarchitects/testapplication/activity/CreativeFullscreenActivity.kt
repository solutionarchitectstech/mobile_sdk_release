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
import tech.solutionarchitects.testapplication.databinding.ActivityCreativeFullscreenBinding
import tech.solutionarchitects.testapplication.hideMessage
import tech.solutionarchitects.testapplication.log
import tech.solutionarchitects.testapplication.showMessage

class CreativeFullscreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreativeFullscreenBinding

    private lateinit var errorLabel: TextView

    private lateinit var creative: Creative

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        errorLabel = TextView(this)

        binding = ActivityCreativeFullscreenBinding.inflate(layoutInflater)

        val creativeView = binding.creativeView
        creativeView.query = CreativeQuery(
            placementId = arrayOf(
                "HTML_BANNER",
                "IMAGE_BANNER"
            ).random(),
            sizes = listOf(Size(width = 260, height = 106)),
            floorPrice = 2.0,
            currency = "USD",
            customParams = mapOf(
                "skuId" to "LG00001",
                "skuName" to "Lego bricks (speed boat)",
                "category" to "Kids",
                "subCategory" to "Lego",
                "gdprConsent" to "CPsmEWIPsmEWIABAMBFRACBsABEAAAAgEIYgACJAAYiAAA.QRXwAgAAgivA",
                "ccpa" to "1YNN",
                "coppa" to "1"
            ),
        )
        creativeView.scaleToFit = true
        creativeView.isScrollEnabled = false
        creativeView.isFullscreenEnabled = true

        creative = Creative(
            lifecycle = lifecycle,
            creativeView = creativeView,
            listener = object : CreativeEventListener {
                override fun onLoadDataSuccess(creativeView: CreativeView) {
                    val placementId = creativeView.query?.placementId
                    log(Log.DEBUG, "onLoadDataSuccess[${placementId}]")

                    hideMessage(errorLabel)
                }

                override fun onLoadDataFail(creativeView: CreativeView, throwable: Throwable?) {
                    val placementId = creativeView.query?.placementId
                    val msg = "onLoadDataFail[${placementId}]: ${throwable?.message}"
                    log(Log.ERROR, msg)

                    showMessage(msg, errorLabel, creativeView, Color.RED)
                }

                override fun onLoadContentSuccess(creativeView: CreativeView) {
                    val placementId = creativeView.query?.placementId
                    log(Log.DEBUG, "onLoadContentSuccess[${placementId}]")

                    hideMessage(errorLabel)
                }

                override fun onLoadContentFail(creativeView: CreativeView, throwable: Throwable?) {
                    val placementId = creativeView.query?.placementId
                    val msg = "onLoadContentFail[${placementId}]: ${throwable?.message}"
                    log(Log.ERROR, msg)

                    showMessage(msg, errorLabel, creativeView, Color.RED)
                }

                override fun onNoAdContent(creativeView: CreativeView) {
                    val placementId = creativeView.query?.placementId
                    val msg = "onNoAdContent[${placementId}]"
                    log(Log.WARN, msg)

                    showMessage(msg, errorLabel, creativeView, Color.RED)
                }

                override fun onClose(creativeView: CreativeView) {
                    val placementId = creativeView.query?.placementId
                    log(Log.DEBUG, "onClose[${placementId}]")

                    hideMessage(errorLabel)
                }
            }
        )

        setContentView(binding.root)

        creative.load()
    }
}