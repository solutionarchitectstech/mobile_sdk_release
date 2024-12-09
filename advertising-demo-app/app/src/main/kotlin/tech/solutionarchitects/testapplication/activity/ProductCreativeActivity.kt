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
import tech.solutionarchitects.testapplication.databinding.ActivityProductCreativeBinding
import tech.solutionarchitects.testapplication.log

class ProductCreativeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductCreativeBinding

    private lateinit var productCreative: ProductCreative

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductCreativeBinding.inflate(layoutInflater, null, false)

        productCreative = ProductCreative(
            query = ProductCreativeQuery(
                placementId = "PRODUCT_01",
                customParams = mapOf(
                    "object" to mapOf(
                        "id" to "ID00001",
                        "name" to "MyObjectName",
                    ),
                    "string" to "MyString",
                    "int" to 199,
                    "float" to 3.14,
                    "objectList" to listOf(
                        mapOf(
                            "property" to "item",
                            "value" to "lego bricks"
                        ),
                        mapOf(
                            "property" to "amount",
                            "value" to 3001
                        ),
                        mapOf(
                            "property" to "price",
                            "value" to 12.99
                        ),
                    ),
                    "integerList" to listOf(11, 12, -13, -14),
                    "nonTypicalList" to listOf("some string", 3.14, 199),
                    "emptyList" to emptyList<Int>(),
                    "asd" to {}
                )
            ),
            listener = object : ProductCreativeEventListener {
                override fun onLoadDataSuccess() {
                    log(Log.DEBUG, "onLoadDataSuccess")
                }

                override fun onLoadDataFail(throwable: Throwable) {
                    log(Log.ERROR, "onLoadDataFail: ${throwable.message}")
                }

                override fun onLoadContentSuccess(entity: ProductCreativeEntity) {
                    log(Log.DEBUG, "onLoadContentSuccess[${entity.placementId}]")
                    binding.productCreativeResultTextView.text = entity.toString()
                }

                override fun onLoadContentFail(query: ProductCreativeQuery, throwable: Throwable?) {
                    log(Log.ERROR, "onLoadContentFail[${query.placementId}]: ${throwable?.message}")
                }

                override fun onNoAdContent(query: ProductCreativeQuery) {
                    log(Log.WARN, "onNoAdContent[${query.placementId}]")
                }
            }
        )

        binding.getProductCreativeButton.setOnClickListener {
            productCreative.load()
        }

        setContentView(binding.root)
    }
}
