package tech.solutionarchitects.testapplication.activity

import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import tech.solutionarchitects.advertisingsdk.api.CloseButtonType
import tech.solutionarchitects.advertisingsdk.api.common.BannerCreative
import tech.solutionarchitects.advertisingsdk.api.common.Size
import tech.solutionarchitects.advertisingsdk.api.feature.banner.BannerCreativeQuery
import tech.solutionarchitects.advertisingsdk.api.feature.banner.BannerView
import tech.solutionarchitects.testapplication.databinding.ActivityProgrammaticallyBannerViewBinding
import tech.solutionarchitects.testapplication.utils.showDebugMessage


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
            customParams = mapOf(
                "skuId" to "LG00001",
                "skuName" to "Lego bricks (speed boat)",
                "category" to "Kids",
                "subСategory" to "Lego",
                "gdprConsent" to "CPsmEWIPsmEWIABAMBFRACBsABEAAAAgEIYgACJAAYiAAA.QRXwAgAAgivA",
                "ccpa" to "1YNN",
                "coppa" to "1"
            ),
            closeButtonType = CloseButtonType.Countdown(5)
        )

        bannerView.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        binding.root.addView(bannerView)

        val bannerCreative = BannerCreative(
            lifecycle = lifecycle,
            banner = bannerView,
            refresh = 10
        ) { event ->
            showDebugMessage(event)
        }

        bannerCreative.load()
    }
}
