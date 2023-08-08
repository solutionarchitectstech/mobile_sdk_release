package tech.solutionarchitects.testapplication.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tech.solutionarchitects.advertisingsdk.api.AdvertisingSDKExperimental
import tech.solutionarchitects.advertisingsdk.api.CloseButtonType
import tech.solutionarchitects.advertisingsdk.api.common.BannerCreative
import tech.solutionarchitects.advertisingsdk.api.common.Size
import tech.solutionarchitects.advertisingsdk.api.feature.banner.BannerCreativeQuery
import tech.solutionarchitects.testapplication.databinding.ActivityMultipleNativeBannerViewBinding
import tech.solutionarchitects.testapplication.utils.showDebugMessage

class MultipleNativeBannerViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMultipleNativeBannerViewBinding

    @OptIn(AdvertisingSDKExperimental::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMultipleNativeBannerViewBinding.inflate(layoutInflater)

        binding.bannerView1.query = BannerCreativeQuery(
            placementId = "YOUR_PLACEMENT_ID",
            sizes = listOf(Size(width = 260, height = 106)),
            floorPrice = 2.0,
            currency = "RUB",
            customParams = mapOf("example" to "value", "example2" to "value2")
        )

        binding.bannerView2.query = BannerCreativeQuery(
            placementId = "YOUR_PLACEMENT_ID",
            sizes = listOf(Size(width = 260, height = 106)),
            floorPrice = 2.0,
            currency = "RUB",
            customParams = mapOf("example" to "value", "example2" to "value2")
        )

        binding.bannerView3.query = BannerCreativeQuery(
            placementId = "YOUR_PLACEMENT_ID",
            sizes = listOf(Size(width = 260, height = 106)),
            floorPrice = 2.0,
            currency = "RUB",
            customParams = mapOf("example" to "value", "example2" to "value2")
        )
        binding.bannerView3.scaleToFit = false

        binding.bannerView4.query = BannerCreativeQuery(
            placementId = "YOUR_PLACEMENT_ID",
            sizes = listOf(Size(width = 260, height = 106)),
            floorPrice = 2.0,
            currency = "RUB",
            customParams = mapOf("example" to "value", "example2" to "value2")
        )
        binding.bannerView4.scaleToFit = false

        val nativeBannerController = BannerCreative(
            lifecycle = lifecycle,
            banners = listOf(
                binding.bannerView1,
                binding.bannerView2,
                binding.bannerView3,
                binding.bannerView4
            )
        ) { event ->
            showDebugMessage(event)
        }

        nativeBannerController.load()

        setContentView(binding.root)
    }
}
