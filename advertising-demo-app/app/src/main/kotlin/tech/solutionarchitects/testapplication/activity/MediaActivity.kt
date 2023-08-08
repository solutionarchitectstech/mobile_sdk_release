package tech.solutionarchitects.testapplication.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import tech.solutionarchitects.advertisingsdk.api.CloseButtonType
import tech.solutionarchitects.advertisingsdk.api.common.MediaCreativeEvent
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
            MediaCreativeQuery(closeButtonType = CloseButtonType.Countdown(10))

        binding.refreshCreativeButton.setOnClickListener {
            mediaCreative.load()
        }

        mediaCreative = MediaCreative(lifecycle, binding.mediaView) { event ->
            val message: String = when (event) {
                MediaCreativeEvent.MediaCloseButtonClick -> "CloseButtonClick"
                is MediaCreativeEvent.MediaLoadContentFail -> "LoadContent: Fail"
                MediaCreativeEvent.MediaLoadContentSuccess -> "LoadContent: Success"
                is MediaCreativeEvent.MediaLoadDataFail -> "LoadData: Fail"
                MediaCreativeEvent.MediaLoadDataSuccess -> "LoadData: Success"
                MediaCreativeEvent.NoAdContent -> "No ad content"
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        mediaCreative.load()
    }
}