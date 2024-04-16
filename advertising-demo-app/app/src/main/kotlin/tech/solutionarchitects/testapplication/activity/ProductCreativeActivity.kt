package tech.solutionarchitects.testapplication.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import tech.solutionarchitects.advertisingsdk.api.feature.product_creative.ProductCreative
import tech.solutionarchitects.advertisingsdk.api.feature.product_creative.ProductCreativeEntity
import tech.solutionarchitects.advertisingsdk.api.feature.product_creative.ProductCreativeEventListener
import tech.solutionarchitects.advertisingsdk.api.feature.product_creative.ProductCreativeQuery
import tech.solutionarchitects.testapplication.databinding.ActivityProductCreativeBinding
import timber.log.Timber

class ProductCreativeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductCreativeBinding

    private lateinit var productCreative: ProductCreative

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductCreativeBinding.inflate(layoutInflater, null, false)

        productCreative = ProductCreative(
            query = ProductCreativeQuery(
                placementId = "PRODUCT_01",
                customParams = mapOf("SOME_CUSTOM_KEY" to "SOME_CUSTOM_VALUE"),
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

    private fun log(priority: Int, message: String) {
        Timber.log(priority, message)
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}
