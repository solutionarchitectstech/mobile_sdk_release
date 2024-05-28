package tech.solutionarchitects.testapplication.activity

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import tech.solutionarchitects.advertisingsdk.api.feature.creative.CreativeEventListener
import tech.solutionarchitects.advertisingsdk.api.common.Size
import tech.solutionarchitects.advertisingsdk.api.feature.creative.Creative
import tech.solutionarchitects.advertisingsdk.api.feature.creative.CreativeQuery
import tech.solutionarchitects.advertisingsdk.api.feature.creative.CreativeView
import tech.solutionarchitects.testapplication.databinding.ActivityMultipleCreativesBinding
import tech.solutionarchitects.testapplication.hideMessage
import tech.solutionarchitects.testapplication.log
import tech.solutionarchitects.testapplication.showMessage

class MultipleCreativesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMultipleCreativesBinding

    private lateinit var errorLabels: Map<Int, TextView>

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

        binding.creativeView5.query = CreativeQuery(
            placementId = "MY_CREATIVE",
            sizes = listOf(Size(width = 260, height = 106)),
        )

        errorLabels = mapOf(
            Pair(binding.creativeView1.hashCode(), TextView(this)),
            Pair(binding.creativeView2.hashCode(), TextView(this)),
            Pair(binding.creativeView3.hashCode(), TextView(this)),
            Pair(binding.creativeView4.hashCode(), TextView(this)),
            Pair(binding.creativeView5.hashCode(), TextView(this)),
        )

        creative = Creative(
            lifecycle = lifecycle,
            creativeViews = listOf(
                binding.creativeView1,
                binding.creativeView2,
                binding.creativeView3,
                binding.creativeView4,
                binding.creativeView5,
            ),
            listener = object : CreativeEventListener {
                override fun onLoadDataSuccess(creativeView: CreativeView) {
                    val placementId = creativeView.query?.placementId
                    log(Log.DEBUG, "onLoadDataSuccess[${placementId}]")

                    val label = errorLabels[creativeView.hashCode()]
                    if (label != null) {
                        hideMessage(label)
                    }
                }

                override fun onLoadDataFail(creativeView: CreativeView, throwable: Throwable?) {
                    val placementId = creativeView.query?.placementId
                    val msg = "onLoadDataFail[${placementId}]: ${throwable?.message}"
                    log(Log.ERROR, msg)

                    val label = errorLabels[creativeView.hashCode()]
                    if (label != null) {
                        showMessage(msg, label, creativeView, Color.RED)
                    }
                }

                override fun onLoadContentSuccess(creativeView: CreativeView) {
                    val placementId = creativeView.query?.placementId
                    log(Log.DEBUG, "onLoadContentSuccess[${placementId}]")

                    val label = errorLabels[creativeView.hashCode()]
                    if (label != null) {
                        hideMessage(label)
                    }
                }

                override fun onLoadContentFail(creativeView: CreativeView, throwable: Throwable?) {
                    val placementId = creativeView.query?.placementId
                    val msg = "onLoadContentFail[${placementId}]: ${throwable?.message}"
                    log(Log.ERROR, msg)

                    val label = errorLabels[creativeView.hashCode()]
                    if (label != null) {
                        showMessage(msg, label, creativeView, Color.RED)
                    }
                }

                override fun onNoAdContent(creativeView: CreativeView) {
                    val placementId = creativeView.query?.placementId
                    val msg = "onNoAdContent[${placementId}]"
                    log(Log.WARN, msg)

                    val label = errorLabels[creativeView.hashCode()]
                    if (label != null) {
                        showMessage(msg, label, creativeView, Color.RED)
                    }
                }

                override fun onClose(creativeView: CreativeView) {
                    val placementId = creativeView.query?.placementId
                    log(Log.DEBUG, "onClose[${placementId}]")

                    val label = errorLabels[creativeView.hashCode()]
                    if (label != null) {
                        hideMessage(label)
                    }
                }
            }
        )

        setContentView(binding.root)

        creative.load()
    }
}