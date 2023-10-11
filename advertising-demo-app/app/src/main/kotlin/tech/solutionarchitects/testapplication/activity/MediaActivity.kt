package tech.solutionarchitects.testapplication.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import tech.solutionarchitects.advertisingsdk.api.CloseButtonType
import tech.solutionarchitects.advertisingsdk.api.common.MediaCreativeEvent
import tech.solutionarchitects.advertisingsdk.api.common.Size
import tech.solutionarchitects.advertisingsdk.api.feature.media.MediaCreative
import tech.solutionarchitects.advertisingsdk.api.feature.media.MediaCreativeQuery
import tech.solutionarchitects.testapplication.databinding.ActivityMediaBinding

class MediaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMediaBinding
    private lateinit var mediaCreative: MediaCreative
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mediaView.query =
            MediaCreativeQuery(
                placementId = "YOUR_PLACEMENT_ID",
                customParams = emptyMap(),
                sizes = listOf(Size(width = 260, height = 106))
            )

        binding.refreshCreativeButton.setOnClickListener {
            mediaCreative.load()
        }

        mediaCreative = MediaCreative(lifecycle, binding.mediaView, refresh = true, skipTime = 2) { event ->
            val message: String = when (event) {
                is MediaCreativeEvent.MediaCloseButtonClick -> "CloseButtonClick"
                is MediaCreativeEvent.MediaLoadContentFail -> "LoadContent: Fail"
                is MediaCreativeEvent.MediaLoadContentSuccess -> "LoadContent: Success"
                is MediaCreativeEvent.MediaLoadDataFail -> "LoadData: Fail"
                is MediaCreativeEvent.MediaLoadDataSuccess -> "LoadData: Success"
                is MediaCreativeEvent.NoAdContent -> "No ad content"
                is MediaCreativeEvent.OnTap -> "OnTap: redirect to ${event.redirectUrl}"
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        mediaCreative.load()
    }
}