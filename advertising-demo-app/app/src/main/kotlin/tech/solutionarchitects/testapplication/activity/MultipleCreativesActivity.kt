package tech.solutionarchitects.testapplication.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import tech.solutionarchitects.advertisingsdk.api.feature.creative.CreativeEventListener
import tech.solutionarchitects.advertisingsdk.api.common.Size
import tech.solutionarchitects.advertisingsdk.api.feature.creative.Creative
import tech.solutionarchitects.advertisingsdk.api.feature.creative.CreativeQuery
import tech.solutionarchitects.advertisingsdk.api.feature.creative.CreativeView
import tech.solutionarchitects.testapplication.databinding.ActivityMultipleCreativesBinding
import timber.log.Timber

class MultipleCreativesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMultipleCreativesBinding

    private lateinit var creative: Creative

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMultipleCreativesBinding.inflate(layoutInflater)

        binding.creativeView1.query = CreativeQuery(
            placementId = "VAST_Inline_Simple",
            sizes = listOf(Size(width = 260, height = 106)),
            floorPrice = 1.99,
            currency = "USD",
            customParams = mapOf(
                "skuId" to "LG00001",
                "skuName" to "Leggo bricks (speed boat)",
                "category" to "Kids",
                "subCategory" to "Lego"
            ),
        )

        binding.creativeView2.query = CreativeQuery(
            placementId = "VAST_Wrapper_Compound",
            sizes = listOf(Size(width = 260, height = 106)),
            floorPrice = 2.84,
            currency = "USD",
        )

        binding.creativeView3.isScrollEnabled = true
        binding.creativeView3.scaleToFit = false
        binding.creativeView3.query = CreativeQuery(
            placementId = "IMAGE_BANNER",
            sizes = listOf(Size(width = 260, height = 106)),
        )

        binding.creativeView4.isScrollEnabled = true
        binding.creativeView4.scaleToFit = false
        binding.creativeView4.query = CreativeQuery(
            placementId = "HTML_BANNER",
            sizes = listOf(Size(width = 260, height = 106)),
        )

        creative = Creative(
            lifecycle = lifecycle,
            creativeViews = listOf(
                binding.creativeView1,
                binding.creativeView2,
                binding.creativeView3,
                binding.creativeView4,
            ),
            listener = object : CreativeEventListener {
                override fun onLoadDataSuccess(creativeView: CreativeView) {
                    val placementId = creativeView.query?.placementId
                    log(Log.DEBUG, "onLoadDataSuccess[${placementId}]")
                }

                override fun onLoadDataFail(creativeView: CreativeView, throwable: Throwable?) {
                    val placementId = creativeView.query?.placementId
                    log(Log.ERROR, "onLoadDataFail[${placementId}]: ${throwable?.message}")
                }

                override fun onLoadContentSuccess(creativeView: CreativeView) {
                    val placementId = creativeView.query?.placementId
                    log(Log.DEBUG, "onLoadContentSuccess[${placementId}]")
                }

                override fun onLoadContentFail(creativeView: CreativeView, throwable: Throwable?) {
                    val placementId = creativeView.query?.placementId
                    log(Log.ERROR, "onLoadContentFail[${placementId}]: ${throwable?.message}")
                }

                override fun onNoAdContent(creativeView: CreativeView) {
                    val placementId = creativeView.query?.placementId
                    log(Log.WARN, "onNoAdContent[${placementId}]")
                }

                override fun onClose(creativeView: CreativeView) {
                    val placementId = creativeView.query?.placementId
                    log(Log.DEBUG, "onClose[${placementId}]")
                }
            }
        )

        setContentView(binding.root)

        creative.load()
    }

    private fun log(priority: Int, message: String) {
        Timber.log(priority, message)
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}