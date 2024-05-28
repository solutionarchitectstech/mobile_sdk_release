package tech.solutionarchitects.testapplication.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import tech.solutionarchitects.advertisingsdk.api.feature.product_creative.ProductCreative
import tech.solutionarchitects.advertisingsdk.api.feature.product_creative.ProductCreativeEntity
import tech.solutionarchitects.advertisingsdk.api.feature.product_creative.ProductCreativeEventListener
import tech.solutionarchitects.advertisingsdk.api.feature.product_creative.ProductCreativeQuery
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

                override fun onLoadContentSuccess(entity: ProductCreativeEntity) {
                    log(Log.DEBUG, "onLoadContentSuccess[${entity.placementId}]")
                    "${binding.productCreativeResultTextView.text}\n\n${entity}".also {
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
