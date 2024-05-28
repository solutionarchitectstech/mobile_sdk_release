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
import tech.solutionarchitects.testapplication.databinding.ActivityCreativeInLayoutBinding
import tech.solutionarchitects.testapplication.hideMessage
import tech.solutionarchitects.testapplication.log
import tech.solutionarchitects.testapplication.showMessage

class CreativeInLayoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreativeInLayoutBinding

    private lateinit var errorLabel: TextView

    private lateinit var creative: Creative

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        errorLabel = TextView(this)

        binding = ActivityCreativeInLayoutBinding.inflate(layoutInflater)

        val creativeView = binding.creativeView
        creativeView.query = CreativeQuery(
            placementId = arrayOf(
                "HTML_BANNER",
                "IMAGE_BANNER"
            ).random(),
            sizes = listOf(Size(width = 260, height = 106)),
            floorPrice = 2.0,
            currency = "USD",
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
        creativeView.scaleToFit = true
        creativeView.isScrollEnabled = false

        creative = Creative(
            lifecycle = lifecycle,
            creativeView = creativeView,
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

        creative.load()
    }
}