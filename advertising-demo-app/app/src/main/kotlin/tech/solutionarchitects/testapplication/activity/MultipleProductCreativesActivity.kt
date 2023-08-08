package tech.solutionarchitects.testapplication.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import tech.solutionarchitects.advertisingsdk.api.AdvertisingSDKExperimental
import tech.solutionarchitects.advertisingsdk.api.feature.product_creative.ProductCreative
import tech.solutionarchitects.advertisingsdk.api.feature.product_creative.ProductCreativeQuery
import tech.solutionarchitects.advertisingsdk.api.feature.product_creative.ProductLoadContentFail
import tech.solutionarchitects.advertisingsdk.api.feature.product_creative.ProductLoadContentSuccess
import tech.solutionarchitects.advertisingsdk.api.feature.product_creative.ProductLoadDataFail
import tech.solutionarchitects.advertisingsdk.api.feature.product_creative.ProductLoadDataSuccess
import tech.solutionarchitects.advertisingsdk.api.feature.product_creative.NoAdContent
import tech.solutionarchitects.testapplication.databinding.ActivityMultipleProductCreativesBinding
import timber.log.Timber

class MultipleProductCreativesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMultipleProductCreativesBinding

    @OptIn(AdvertisingSDKExperimental::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMultipleProductCreativesBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)

        val productCreative = ProductCreative(
            queries = listOf(
                ProductCreativeQuery(
                    placementId = "YOUR_PLACEMENT_ID",
                    customParams = mapOf("SOME_CUSTOM_KEY" to "SOME_CUSTOM_VALUE"),
                ),
                ProductCreativeQuery(
                    placementId = "YOUR_PLACEMENT_ID",
                    customParams = mapOf("SOME_CUSTOM_KEY" to "SOME_CUSTOM_VALUE"),
                )
            )
        ) { event ->
            when (event) {
                is ProductLoadDataSuccess -> {
                    Toast.makeText(applicationContext, "Data from server loaded", Toast.LENGTH_LONG)
                        .show()
                }

                is ProductLoadContentFail -> {
                    Timber.e(event.throwable)
                    Toast.makeText(
                        applicationContext,
                        "Content loading failed for: ${event.query.placementId}",
                        Toast.LENGTH_LONG
                    ).show()
                }

                is ProductLoadContentSuccess -> {
                    binding.productCreativeResultTextView.text =
                        binding.productCreativeResultTextView.text.toString()
                            .plus("\n\n" + event.entity.toString())
                }

                is ProductLoadDataFail -> {
                    Timber.e(event.throwable)
                    Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_LONG)
                        .show()
                }

                is NoAdContent -> {
                    Toast.makeText(applicationContext, "No advertisement for: ${event.query.placementId}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

        binding.multipleProductCreativesButton.setOnClickListener {
            binding.productCreativeResultTextView.text = ""
            productCreative.load()
        }
    }
}
