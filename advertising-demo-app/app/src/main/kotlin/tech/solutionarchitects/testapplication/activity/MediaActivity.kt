package tech.solutionarchitects.testapplication.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import tech.solutionarchitects.advertisingsdk.api.CloseButtonType
import tech.solutionarchitects.advertisingsdk.api.common.MediaCloseButtonClick
import tech.solutionarchitects.advertisingsdk.api.common.MediaLoadContentFail
import tech.solutionarchitects.advertisingsdk.api.common.MediaLoadContentSuccess
import tech.solutionarchitects.advertisingsdk.api.common.MediaLoadDataFail
import tech.solutionarchitects.advertisingsdk.api.common.MediaLoadDataSuccess
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
                MediaCloseButtonClick -> "CloseButtonClick"
                is MediaLoadContentFail -> "LoadContent: Fail"
                MediaLoadContentSuccess -> "LoadContent: Success"
                is MediaLoadDataFail -> "LoadData: Fail"
                MediaLoadDataSuccess -> "LoadData: Success"
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        mediaCreative.load()
    }
}