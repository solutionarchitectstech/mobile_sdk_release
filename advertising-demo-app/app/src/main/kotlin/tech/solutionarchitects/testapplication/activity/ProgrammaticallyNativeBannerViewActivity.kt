package tech.solutionarchitects.testapplication.activity

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import tech.solutionarchitects.advertisingsdk.api.AdvertisingSDKExperimental
import tech.solutionarchitects.advertisingsdk.api.CloseButtonType
import tech.solutionarchitects.advertisingsdk.api.common.BannerCreative
import tech.solutionarchitects.advertisingsdk.api.common.Size
import tech.solutionarchitects.advertisingsdk.api.feature.banner.BannerCreativeQuery
import tech.solutionarchitects.advertisingsdk.api.feature.nativebanner.NativeBannerView
import tech.solutionarchitects.testapplication.databinding.ActivityProgrammaticallyNativeBannerViewBinding
import tech.solutionarchitects.testapplication.utils.showDebugMessage
import tech.solutionarchitects.testapplication.utils.toDP

class ProgrammaticallyNativeBannerViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProgrammaticallyNativeBannerViewBinding

    @OptIn(AdvertisingSDKExperimental::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgrammaticallyNativeBannerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val nativeBannerView = NativeBannerView(this)
        nativeBannerView.query = BannerCreativeQuery(
            placementId = "YOUR_PLACEMENT_ID",
            sizes = listOf(Size(width = 260, height = 106)),
            floorPrice = 2.0,
            currency = "RUB",
            customParams = mapOf("example" to "value", "example2" to "value2"),
            closeButtonType = CloseButtonType.Visible
        )

        val layoutParams = LinearLayout.LayoutParams(260.toDP(this), 106.toDP(this))

        nativeBannerView.layoutParams = layoutParams

        binding.root.addView(nativeBannerView)

        val bannerCreative = BannerCreative(
            lifecycle = lifecycle,
            banner = nativeBannerView,
            refresh = 30
        ) { event ->
            showDebugMessage(event)
        }

        bannerCreative.load()
    }
}
