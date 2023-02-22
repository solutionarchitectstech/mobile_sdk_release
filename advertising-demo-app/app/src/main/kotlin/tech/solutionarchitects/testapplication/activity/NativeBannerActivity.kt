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
            placementId = "YOUR_PLACEMENT_ID",
            closeButtonType = CloseButtonType.Countdown(milliseconds = 3_000),
            //floorPrice = 2f,
            //currency = "RUB",
            //customParams = mapOf("example" to "value", "example2" to "value2")
        ) { event ->
            println("NativeBannerEvent: $event")
        }
    }
}