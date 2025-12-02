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
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import tech.solutionarchitects.advertisingsdk.product.presentation.ProductCreative
import tech.solutionarchitects.advertisingsdk.product.domain.model.ProductCreativeEntity
import tech.solutionarchitects.advertisingsdk.product.presentation.ProductCreativeEventListener
import tech.solutionarchitects.advertisingsdk.product.domain.model.ProductCreativeQuery
import tech.solutionarchitects.testapplication.databinding.ActivityMultipleProductCreativesBinding
import tech.solutionarchitects.testapplication.log

class MultipleProductCreativesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMultipleProductCreativesBinding

    private lateinit var productCreative: ProductCreative

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMultipleProductCreativesBinding.inflate(layoutInflater, null, false)

        productCreative = ProductCreative(
            queries = listOf(
                ProductCreativeQuery(
                    placementId = "PRODUCT_01",
                    customParams = mapOf("SOME_CUSTOM_KEY" to "SOME_CUSTOM_VALUE"),
                ),
                ProductCreativeQuery(
                    placementId = "PRODUCT_02",
                    customParams = mapOf("SOME_CUSTOM_KEY" to "SOME_CUSTOM_VALUE"),
                )
            ),
            listener = object : ProductCreativeEventListener {
                override fun onLoadDataSuccess() {
                    log(Log.DEBUG, "onLoadDataSuccess")
                }

                override fun onLoadDataFail(throwable: Throwable) {
                    log(Log.ERROR, "onLoadDataFail: ${throwable.message}")
                }

                override fun onLoadContentSuccess(entity: ProductCreativeEntity, ext: Map<String, Any>) {
                    val trackingId = ext["trackingId"] as? String ?: ""
                    val creativeId = ext["creativeId"] as? String ?: ""
                    "${binding.productCreativeResultTextView.text}\n\ntrackingId: ${trackingId}\ncreativeId: ${creativeId}\n${entity}".also {
                        binding.productCreativeResultTextView.text = it
                    }
                }

                override fun onLoadContentFail(query: ProductCreativeQuery, throwable: Throwable?) {
                    log(Log.ERROR, "onLoadContentFail[${query.placementId}]: ${throwable?.message}")
                }

                override fun onNoAdContent(query: ProductCreativeQuery) {
                    log(Log.WARN, "onNoAdContent[${query.placementId}]")
                }
            }
        )

        binding.getProductCreativesButton.setOnClickListener {
            binding.productCreativeResultTextView.text = ""
            productCreative.load()
        }

        setContentView(binding.root)
    }
}
