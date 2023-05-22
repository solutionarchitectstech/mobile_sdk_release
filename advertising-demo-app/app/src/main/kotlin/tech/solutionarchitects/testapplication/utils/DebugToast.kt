package tech.solutionarchitects.testapplication.utils

import android.content.Context
import android.widget.Toast
import tech.solutionarchitects.advertisingsdk.api.common.BannerCloseButtonClick
import tech.solutionarchitects.advertisingsdk.api.common.BannerLoadContentFail
import tech.solutionarchitects.advertisingsdk.api.common.BannerLoadContentSuccess
import tech.solutionarchitects.advertisingsdk.api.common.BannerLoadDataFail
import tech.solutionarchitects.advertisingsdk.api.common.BannerLoadDataSuccess
import tech.solutionarchitects.advertisingsdk.api.common.BannerSentClickStatistic
import tech.solutionarchitects.advertisingsdk.api.common.BannerSentLoadStatistic
import tech.solutionarchitects.advertisingsdk.api.common.BannerSentViewStatistic
import tech.solutionarchitects.advertisingsdk.api.common.TechAdvertisingListenerEvent

fun Context.showDebugToast(event: TechAdvertisingListenerEvent, length: Int = Toast.LENGTH_SHORT) {
    val message: String = when (event) {
        is BannerCloseButtonClick -> "PlacementID: ${event.placementId}\nClose button clicked!"
        is BannerLoadContentFail -> "PlacementID: ${event.placementId}\nUnable to display content!"
        is BannerLoadContentSuccess -> "PlacementID: ${event.placementId}\nBanner successfully displayed!"
        is BannerLoadDataFail -> "PlacementID: ${event.placementId}\nUnable to load data from server!"
        is BannerLoadDataSuccess -> "PlacementID: ${event.placementId}\nData from server loaded"
        is BannerSentClickStatistic -> "PlacementID: ${event.placementId}\nClick stats sent!"
        is BannerSentLoadStatistic -> "PlacementID: ${event.placementId}\nLoad stats sent!"
        is BannerSentViewStatistic -> "PlacementID: ${event.placementId}\nView stats sent!"
    }
    Toast.makeText(this, message, length).show()
}
