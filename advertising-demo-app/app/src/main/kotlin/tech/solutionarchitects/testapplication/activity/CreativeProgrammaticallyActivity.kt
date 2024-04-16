package tech.solutionarchitects.testapplication.activity

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import tech.solutionarchitects.advertisingsdk.api.feature.creative.CreativeEventListener
import tech.solutionarchitects.advertisingsdk.api.common.Size
import tech.solutionarchitects.advertisingsdk.api.feature.creative.Creative
import tech.solutionarchitects.advertisingsdk.api.feature.creative.CreativeQuery
import tech.solutionarchitects.advertisingsdk.api.feature.creative.CreativeView
import tech.solutionarchitects.testapplication.databinding.ActivityCreativeProgrammaticallyBinding
import timber.log.Timber

class CreativeProgrammaticallyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreativeProgrammaticallyBinding

    private lateinit var creative: Creative

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreativeProgrammaticallyBinding.inflate(layoutInflater)

        val creativeView = CreativeView(this)
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

        creativeView.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        binding.root.addView(creativeView)

        setContentView(binding.root)

        creative.load()
    }

    private fun log(priority: Int, message: String) {
        Timber.log(priority, message)
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}