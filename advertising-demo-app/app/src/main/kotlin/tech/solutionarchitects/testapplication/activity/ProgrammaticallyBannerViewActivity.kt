package tech.solutionarchitects.testapplication.activity

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import tech.solutionarchitects.advertisingsdk.api.CloseButtonType
import tech.solutionarchitects.advertisingsdk.api.common.BannerCreative
import tech.solutionarchitects.advertisingsdk.api.common.Size
import tech.solutionarchitects.advertisingsdk.api.feature.banner.BannerCreativeQuery
import tech.solutionarchitects.advertisingsdk.api.feature.banner.BannerView
import tech.solutionarchitects.testapplication.databinding.ActivityProgrammaticallyBannerViewBinding
import tech.solutionarchitects.testapplication.utils.showDebugMessage
import tech.solutionarchitects.testapplication.utils.toDP

class ProgrammaticallyBannerViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProgrammaticallyBannerViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgrammaticallyBannerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bannerView = BannerView(this)
        bannerView.query = BannerCreativeQuery(
            placementId = "YOUR_PLACEMENT_ID",
            sizes = listOf(Size(width = 260, height = 106)),
            floorPrice = 2.0,
            currency = "RUB",
            customParams = mapOf("example" to "value", "example2" to "value2"),
            closeButtonType = CloseButtonType.Appearing(5)
        )

        val layoutParams = LinearLayout.LayoutParams(260.toDP(this), 106.toDP(this))

        bannerView.layoutParams = layoutParams

        binding.root.addView(bannerView)

        val bannerCreative = BannerCreative(
            lifecycle = lifecycle,
            banner = bannerView,
            refresh = 15
        ) { event ->
            showDebugMessage(event)
        }

        bannerCreative.load()
    }
}
