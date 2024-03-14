package tech.solutionarchitects.testapplication.activity

import android.os.Bundle
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductCreativeBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)


        val productCreative = ProductCreative(
            query = ProductCreativeQuery(
                placementId = "PRODUCT_01",
                customParams = mapOf("SOME_CUSTOM_KEY" to "SOME_CUSTOM_VALUE"),
            ),
            listener = object : ProductCreativeEventListener {
                override fun onLoadDataSuccess() {
                    Toast.makeText(applicationContext, "Data from server loaded", Toast.LENGTH_LONG)
                        .show()
                }

                override fun onLoadDataFail(throwable: Throwable) {
                    Timber.e(throwable)
                    Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_LONG)
                        .show()
                }

                override fun onLoadContentSuccess(entity: ProductCreativeEntity) {
                    binding.productCreativeResultTextView.text = entity.toString()
                }

                override fun onLoadContentFail(query: ProductCreativeQuery, throwable: Throwable?) {
                    Timber.e(throwable)
                    Toast.makeText(
                        applicationContext,
                        "Content loading failed for: ${query.placementId}",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onNoAdContent(query: ProductCreativeQuery) {
                    Toast.makeText(
                        applicationContext,
                        "No advertisement for: ${query.placementId}",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
        )

        binding.productCreativeButton.setOnClickListener {
            productCreative.load()
        }
    }
}
