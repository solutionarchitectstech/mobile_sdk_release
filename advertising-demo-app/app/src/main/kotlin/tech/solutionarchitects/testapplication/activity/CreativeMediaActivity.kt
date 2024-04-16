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
import tech.solutionarchitects.testapplication.databinding.ActivityCreativeMediaBinding
import timber.log.Timber

class CreativeMediaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreativeMediaBinding

    private lateinit var creative: Creative

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreativeMediaBinding.inflate(layoutInflater)

        binding.vastInlineSimpleButton.setOnClickListener {
            load(placementId = "VAST_Inline_Simple")
        }
        binding.vastWrapperSimpleButton.setOnClickListener {
            load(placementId = "VAST_Wrapper_Simple")
        }
        binding.vastWrapperCompoundButton.setOnClickListener {
            load(placementId = "VAST_Wrapper_Compound")
        }
        binding.vastWrapperChainLess5Button.setOnClickListener {
            load(placementId = "VAST_Wrapper_Chain_Less_5")
        }
        binding.vastWrapperChainGreater5Button.setOnClickListener {
            load(placementId = "VAST_Wrapper_Chain_Greater_5")
        }
        binding.vastWrapperChainLoopButton.setOnClickListener {
            load(placementId = "VAST_Wrapper_Chain_Loop")
        }

        creative = Creative(
            lifecycle = lifecycle,
            creativeView = binding.creativeView,
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

        load(placementId = "VAST_Inline_Simple")
    }

    private fun load(placementId: String) {
        binding.vastLabelTextView.text = placementId

        binding.creativeView.query = CreativeQuery(
            placementId = placementId,
            sizes = listOf(Size(width = 260, height = 106)),
            floorPrice = 1.99,
            currency = "USD",
        )

        creative.load()
    }

    private fun log(priority: Int, message: String) {
        Timber.log(priority, message)
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}