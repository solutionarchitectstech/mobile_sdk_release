package tech.solutionarchitects.testapplication.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import tech.solutionarchitects.advertisingsdk.api.TechAdvertising
import tech.solutionarchitects.advertisingsdk.api.feature.product_creative.CreativePlace
import tech.solutionarchitects.advertisingsdk.api.feature.product_creative.ProductCreativeQuery
import tech.solutionarchitects.testapplication.databinding.ActivityProductCreativeBinding

class ProductCreativeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductCreativeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductCreativeBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)


        val productCreativeService = TechAdvertising.getProductCreativeService()
        binding.productCreativeButton.setOnClickListener {
            lifecycleScope.launch { // Or your own scope
                productCreativeService.fetch(
                    query = ProductCreativeQuery(
                        places = listOf(
                            CreativePlace(
                                placementId = "SOME_PLACEMENT_ID",
                                customParams = mapOf("SOME_CUSTOM_KEY" to "SOME_CUSTOM_VALUE"),
                            )
                        )
                    )
                ).onSuccess { productCreative ->
                    // Handle success
                    binding.productCreativeResultTextView.text= productCreative.toString()
                }.onFailure { exception: Throwable ->
                    // Handle failure
                }
            }
        }
    }


}
