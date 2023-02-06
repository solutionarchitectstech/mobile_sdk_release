package tech.solutionarchitects.testapplication.activity

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import tech.solutionarchitects.advertisingsdk.core.model.Size
import tech.solutionarchitects.advertisingsdk.listener.*
import tech.solutionarchitects.advertisingsdk.types.CloseButtonType
import tech.solutionarchitects.testapplication.databinding.ActivityScrollableWithBannersBinding

class ScrollableActivityWithBanners : AppCompatActivity() {

    private lateinit var binding: ActivityScrollableWithBannersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScrollableWithBannersBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.bannerView.load(
            placementId = "1",
            sizes = listOf(Size(width = 1024, height = 768)),
            closeButtonType = CloseButtonType.Countdown(milliseconds = 30_000),
            //customParams = mapOf("example" to "value", "example2" to "value2")
        ) { event ->
            when (event) {
                is BannerLoadDataSuccess -> {
                    println("BannerLoadDataSuccess: ${event.placementId}")
                }
                is BannerLoadDataFail -> {
                    println("BannerLoadDataFail: ${event.throwable}")
                }
                is BannerLoadContentSuccess -> {
                    println("BannerLoadContentSuccess: ${event.placementId}")
                }
                is BannerLoadContentFail -> {
                    println("BannerLoadContentFail: ${event.throwable}")
                }
                is BannerCloseButtonClick -> {
                    println("BannerCloseButtonClick: ${event.placementId}")
                    finish()
                }
                else -> {}
            }
        }
    }
}