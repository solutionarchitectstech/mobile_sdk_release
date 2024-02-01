package tech.solutionarchitects.testapplication.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tech.solutionarchitects.advertisingsdk.api.common.CreativeEventListener
import tech.solutionarchitects.advertisingsdk.api.common.Size
import tech.solutionarchitects.advertisingsdk.api.feature.creative.Creative
import tech.solutionarchitects.advertisingsdk.api.feature.creative.CreativeQuery
import tech.solutionarchitects.advertisingsdk.api.feature.creative.CreativeView
import tech.solutionarchitects.testapplication.databinding.ActivityCreativeBinding
import timber.log.Timber

class CreativeActivity : AppCompatActivity() {

    companion object {
        const val TAG = "CREATIVE_DEBUG_LOGS"
    }

    private lateinit var binding: ActivityCreativeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreativeBinding.inflate(layoutInflater)

        val creativeView = binding.creativeView
        creativeView.scaleToFit = true
        creativeView.isScrollEnabled = false

        val creative = Creative(
            lifecycle = lifecycle,
            creativeView = creativeView,
            listener = object : CreativeEventListener {
                override fun onLoadDataSuccess(creativeView: CreativeView) {
                    Timber.tag(TAG).d("onLoadDataSuccess")
                }

                override fun onLoadDataFail(creativeView: CreativeView, throwable: Throwable?) {
                    Timber.tag(TAG).e("onLoadDataFail: ${throwable.toString()}")
                }

                override fun onLoadContentSuccess(creativeView: CreativeView) {
                    Timber.tag(TAG).d("onLoadContentSuccess")
                }

                override fun onLoadContentFail(creativeView: CreativeView, throwable: Throwable?) {
                    Timber.tag(TAG).e("onLoadContentFail: ${throwable.toString()}")
                }

                override fun onNoAdContent(creativeView: CreativeView) {
                    Timber.tag(TAG).e("onNoAdContent")
                }

                override fun onClose(creativeView: CreativeView) {
                    Timber.tag(TAG).d("Creative was closed")
                }
            }
        )

        binding.loadBannerButton.setOnClickListener {
            creativeView.query = CreativeQuery(
                placementId = "HTML_BANNER",
                sizes = listOf(Size(width = 260, height = 106)),
                floorPrice = 2.0,
                currency = "RUB",
                customParams = mapOf(
                    "skuId" to "LG00001",
                    "skuName" to "Lego bricks (speed boat)",
                    "category" to "Kids",
                    "subCategory" to "Lego",
                    "gdprConsent" to "CPsmEWIPsmEWIABAMBFRACBsABEAAAAgEIYgACJAAYiAAA.QRXwAgAAgivA",
                    "ccpa" to "1YNN",
                    "coppa" to "1"
                ),
            )

            creative.load()
        }

        binding.loadImageBannerButton.setOnClickListener {
            creativeView.query = CreativeQuery(
                placementId = "IMAGE_BANNER",
                sizes = listOf(Size(width = 260, height = 106)),
                floorPrice = 2.0,
                currency = "RUB",
            )

            creative.load()
        }

        binding.loadMediaButton.setOnClickListener {
            creativeView.query = CreativeQuery(
                placementId = "VAST_Wrapper_Compound",
                customParams = emptyMap(),
                sizes = listOf(Size(width = 260, height = 106))
            )

            creative.load()
        }

        setContentView(binding.root)
    }
}