package tech.solutionarchitects.testapplication.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import tech.solutionarchitects.advertisingsdk.TechAdvertising
import tech.solutionarchitects.advertisingsdk.creative.domain.model.CreativeQuery
import tech.solutionarchitects.advertisingsdk.creative.domain.model.Size
import tech.solutionarchitects.advertisingsdk.creative.presentation.Creative
import tech.solutionarchitects.advertisingsdk.creative.presentation.CreativeEventListener
import tech.solutionarchitects.advertisingsdk.creative.presentation.CreativeView
import tech.solutionarchitects.advertisingsdk.remoteconfig.domain.model.CoreDestination
import tech.solutionarchitects.advertisingsdk.remoteconfig.domain.model.InitConfig
import tech.solutionarchitects.testapplication.TechApp
import tech.solutionarchitects.testapplication.databinding.ActivityCreativeManualBinding
import tech.solutionarchitects.testapplication.log

class CreativeManualActivity : AppCompatActivity(), CreativeEventListener {
    private lateinit var binding: ActivityCreativeManualBinding
    private lateinit var creativeView: CreativeView
    private lateinit var creative: Creative

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreativeManualBinding.inflate(layoutInflater)

        binding.bannerUrlEditText.setText(TechApp.BANNER_URL)
        binding.userIdEditText.setText(TechAdvertising.uid)

        creativeView = binding.creativeView
        creativeView.scaleToFit = true
        creativeView.isScrollEnabled = false
        creative = Creative(
            lifecycle = lifecycle,
            creativeView = creativeView,
            listener = this
        )

        binding.getCreativeButton.setOnClickListener {
            initSdk()
            loadCreative()
        }

        setContentView(binding.root)
    }

    private fun initSdk() {
        val bannerUrl = binding.bannerUrlEditText.text.toString().trim()
        TechAdvertising.init(
            storeUrl = "YOUR_STORE_URL",
            initConfig = InitConfig(
                core = CoreDestination(
                    bannerUrl = bannerUrl,
                    productUrl = TechApp.PRODUCT_URL,
                ),
            ),
            debugMode = true,
            locationEnabled = false,
        )

        val userId = binding.userIdEditText.text.toString().trim()
        TechAdvertising.uid = userId.ifBlank { null }
    }

    private fun loadCreative() {
        showLoading()
        val placementId = binding.placementIdEditText.text.toString().trim()
        val width = binding.bannerWidthEditText.text.toString().trim().toIntOrNull()
        if (width == null) {
            hideLoading(errorMessage = "Width parameter is required")
            return
        }
        val height = binding.bannerHeightEditText.text.toString().trim().toIntOrNull()
        if (height == null) {
            hideLoading(errorMessage = "Height parameter is required")
            return
        }
        creativeView.query = CreativeQuery(
            placementId = placementId,
            sizes = listOf(Size(width = width, height = height))
        )
        creative.load()
    }

    private fun showLoading() {
        disableForm()
        creativeView.visibility = View.GONE
        binding.errorTextView.visibility = View.GONE
        binding.errorTextView.text = ""
        binding.loadingSpinner.visibility = View.VISIBLE
    }

    private fun hideLoading(errorMessage: String? = null) {
        binding.loadingSpinner.visibility = View.GONE
        if (errorMessage != null) {
            creativeView.visibility = View.GONE
            binding.errorTextView.text = errorMessage
            binding.errorTextView.visibility = View.VISIBLE
        } else {
            creativeView.visibility = View.VISIBLE
            binding.errorTextView.text = ""
            binding.errorTextView.visibility = View.GONE
        }
        enableForm()
    }

    private fun hideCreative() {
        creativeView.visibility = View.GONE
        binding.errorTextView.visibility = View.GONE
        binding.errorTextView.text = ""
        binding.loadingSpinner.visibility = View.GONE
        enableForm()
    }

    private fun enableForm() {
        binding.queryFormLayout.setChildrenEnabled(true)
    }

    private fun disableForm() {
        binding.queryFormLayout.setChildrenEnabled(false)
    }

    private fun ViewGroup.setChildrenEnabled(enabled: Boolean) {
        for (i in 0 until childCount) {
            getChildAt(i).apply {
                isEnabled = enabled
                if (this is ViewGroup) setChildrenEnabled(enabled)
            }
        }
    }

    // MARK: - CreativeEventListener

    override fun onLoadDataSuccess(creativeView: CreativeView) {
        val placementId = creativeView.query?.placementId
        log(Log.DEBUG, "onLoadDataSuccess[${placementId}]")
    }

    override fun onLoadDataFail(creativeView: CreativeView, throwable: Throwable?) {
        val placementId = creativeView.query?.placementId
        val msg = "onLoadDataFail[${placementId}]: ${throwable?.message}"
        log(Log.ERROR, msg)
        hideLoading(errorMessage = msg)
    }

    override fun onLoadContentSuccess(creativeView: CreativeView, ext: Map<String, Any>) {
        val placementId = creativeView.query?.placementId
        val trackingId = ext["trackingId"] as? String ?: ""
        val creativeId = ext["creativeId"] as? String ?: ""
        log(Log.DEBUG, "onLoadContentSuccess[placementId: ${placementId}, trackingId: ${trackingId}, creativeId: ${creativeId}]")
        hideLoading()
    }

    override fun onLoadContentFail(creativeView: CreativeView, throwable: Throwable?) {
        val placementId = creativeView.query?.placementId
        val msg = "onLoadContentFail[${placementId}]: ${throwable?.message}"
        log(Log.ERROR, msg)
        hideLoading(errorMessage = msg)
    }

    override fun onNoAdContent(creativeView: CreativeView) {
        val placementId = creativeView.query?.placementId
        val msg = "onNoAdContent[${placementId}]"
        log(Log.WARN, msg)
        hideLoading(errorMessage = msg)
    }

    override fun onClose(creativeView: CreativeView) {
        val placementId = creativeView.query?.placementId
        log(Log.DEBUG, "onClose[${placementId}]")
        hideCreative()
    }
}