package tech.solutionarchitects.testapplication.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import tech.solutionarchitects.advertisingsdk.api.feature.product_creative.ProductCreative
import tech.solutionarchitects.advertisingsdk.api.feature.product_creative.ProductCreativeQuery
import tech.solutionarchitects.advertisingsdk.api.feature.product_creative.ProductLoadContentFail
import tech.solutionarchitects.advertisingsdk.api.feature.product_creative.ProductLoadContentSuccess
import tech.solutionarchitects.advertisingsdk.api.feature.product_creative.ProductLoadDataFail
import tech.solutionarchitects.advertisingsdk.api.feature.product_creative.ProductLoadDataSuccess
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
                placementId = "YOUR_PLACEMENT_ID",
                customParams = mapOf("SOME_CUSTOM_KEY" to "SOME_CUSTOM_VALUE"),
            )
        ) { event ->
            when (event) {
                is ProductLoadDataFail -> {
                    Timber.e(event.throwable)
                    Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_LONG)
                        .show()
                }

                is ProductLoadDataSuccess -> {
                    Toast.makeText(applicationContext, "Data from server loaded", Toast.LENGTH_LONG)
                        .show()
                }

                is ProductLoadContentFail -> Toast.makeText(
                    applicationContext,
                    "No advertisement for ${event.query.placementId}",
                    Toast.LENGTH_SHORT
                ).show()

                is ProductLoadContentSuccess -> {
                    binding.productCreativeResultTextView.text = event.entity.toString()
                }
            }
        }

        binding.productCreativeButton.setOnClickListener {
            productCreative.load()
        }
    }
}
