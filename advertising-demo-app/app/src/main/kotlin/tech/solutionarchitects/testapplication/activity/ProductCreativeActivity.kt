package tech.solutionarchitects.testapplication.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import tech.solutionarchitects.advertisingsdk.api.feature.product_creative.OnLoadDataFail
import tech.solutionarchitects.advertisingsdk.api.feature.product_creative.OnLoadDataSuccess
import tech.solutionarchitects.advertisingsdk.api.feature.product_creative.ProductCreative
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
                placementId = "SOME_PLACEMENT_ID",
                customParams = mapOf("SOME_CUSTOM_KEY" to "SOME_CUSTOM_VALUE"),
            )
        ) { event ->
            when (event) {
                is OnLoadDataFail -> {
                    Timber.e(event.throwable)
                    Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_LONG)
                        .show()
                }

                is OnLoadDataSuccess -> {
                    binding.productCreativeResultTextView.text = event.entity.toString()
                }
            }
        }

        binding.productCreativeButton.setOnClickListener {
            productCreative.load()
        }
    }
}
