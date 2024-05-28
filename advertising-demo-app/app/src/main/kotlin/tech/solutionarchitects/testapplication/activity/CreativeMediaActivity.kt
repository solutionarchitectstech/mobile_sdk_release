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
import tech.solutionarchitects.testapplication.databinding.ActivityCreativeMediaBinding
import tech.solutionarchitects.testapplication.hideMessage
import tech.solutionarchitects.testapplication.log
import tech.solutionarchitects.testapplication.showMessage

class CreativeMediaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreativeMediaBinding

    private lateinit var errorLabel: TextView

    private lateinit var creative: Creative

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        errorLabel = TextView(this)

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

                    hideMessage(errorLabel)
                }

                override fun onLoadDataFail(creativeView: CreativeView, throwable: Throwable?) {
                    val placementId = creativeView.query?.placementId
                    val msg = "onLoadDataFail[${placementId}]: ${throwable?.message}"
                    log(Log.ERROR, msg)

                    showMessage(msg, errorLabel, creativeView, Color.RED)
                }

                override fun onLoadContentSuccess(creativeView: CreativeView) {
                    val placementId = creativeView.query?.placementId
                    log(Log.DEBUG, "onLoadContentSuccess[${placementId}]")

                    hideMessage(errorLabel)
                }

                override fun onLoadContentFail(creativeView: CreativeView, throwable: Throwable?) {
                    val placementId = creativeView.query?.placementId
                    val msg = "onLoadContentFail[${placementId}]: ${throwable?.message}"
                    log(Log.ERROR, msg)

                    showMessage(msg, errorLabel, creativeView, Color.RED)
                }

                override fun onNoAdContent(creativeView: CreativeView) {
                    val placementId = creativeView.query?.placementId
                    val msg = "onNoAdContent[${placementId}]"
                    log(Log.WARN, msg)

                    showMessage(msg, errorLabel, creativeView, Color.RED)
                }

                override fun onClose(creativeView: CreativeView) {
                    val placementId = creativeView.query?.placementId
                    log(Log.DEBUG, "onClose[${placementId}]")

                    hideMessage(errorLabel)
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
}