package tech.solutionarchitects.testapplication.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import tech.solutionarchitects.advertisingsdk.api.TechAdvertising
import tech.solutionarchitects.advertisingsdk.api.feature.product_creative.ProductCreativeQuery
import tech.solutionarchitects.testapplication.databinding.ActivityProductCreativeBinding
import timber.log.Timber

class ProductCreativeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductCreativeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductCreativeBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)


        val productCreativeService = TechAdvertising.productCreativeService
        binding.productCreativeButton.setOnClickListener {
            lifecycleScope.launch {
                productCreativeService.fetch(
                    query = ProductCreativeQuery(
                        placementId = "SOME_PLACEMENT_ID",
                        customParams = mapOf("SOME_CUSTOM_KEY" to "SOME_CUSTOM_VALUE"),
                    )
                ).onSuccess {
                    binding.productCreativeResultTextView.text = it.toString()
                }.onFailure { exception: Throwable ->
                    Timber.e(exception)
                    Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }


}
