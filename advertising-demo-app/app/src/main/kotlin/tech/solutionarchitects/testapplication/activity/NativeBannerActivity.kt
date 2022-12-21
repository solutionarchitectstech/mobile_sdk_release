package tech.solutionarchitects.testapplication.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tech.solutionarchitects.advertisingsdk.listener.*
import tech.solutionarchitects.advertisingsdk.types.CloseButtonType
import tech.solutionarchitects.testapplication.databinding.ActivityNativeBannerBinding

class NativeBannerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNativeBannerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNativeBannerBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        binding.nativeBannerView.load(
            placementId = "TestBanner",
            closeButtonType = CloseButtonType.Countdown(milliseconds = 3_000)
        ) { event ->
            when (event) {
                is BannerLoadDataSuccess -> {
                    println("LoadDataSuccess: ${event.placementId}")
                }
                is BannerLoadDataFail -> {
                    println("LoadDataFail: ${event.throwable}")
                }
                is BannerLoadContentSuccess -> {
                    println("LoadContentSuccess: ${event.placementId}")
                }
                is BannerLoadContentFail -> {
                    println("LoadContentFail: ${event.throwable}")
                }
                is BannerCloseButtonClick -> {
                    println("CloseButtonClick: ${event.placementId}")
                    finish()
                }
                else -> {}
            }
        }
    }
}