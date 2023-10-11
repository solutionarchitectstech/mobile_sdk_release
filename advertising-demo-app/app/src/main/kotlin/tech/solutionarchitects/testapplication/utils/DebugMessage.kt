package tech.solutionarchitects.testapplication.utils

import tech.solutionarchitects.advertisingsdk.api.common.BannerClose
import tech.solutionarchitects.advertisingsdk.api.common.BannerCreativeEvent
import tech.solutionarchitects.advertisingsdk.api.common.BannerLoadContentFail
import tech.solutionarchitects.advertisingsdk.api.common.BannerLoadContentSuccess
import tech.solutionarchitects.advertisingsdk.api.common.BannerLoadDataFail
import tech.solutionarchitects.advertisingsdk.api.common.BannerLoadDataSuccess
import tech.solutionarchitects.advertisingsdk.api.common.BannerSentClickStatistic
import tech.solutionarchitects.advertisingsdk.api.common.BannerSentLoadStatistic
import tech.solutionarchitects.advertisingsdk.api.common.BannerSentViewStatistic
import tech.solutionarchitects.advertisingsdk.api.common.NoAdContent
import tech.solutionarchitects.advertisingsdk.api.common.OnTap
import timber.log.Timber

fun showDebugMessage(event: BannerCreativeEvent) {
    val message: String = when (event) {
        is BannerClose -> "PlacementID: ${event.view.query?.placementId}\nClose button clicked!"
        is BannerLoadContentFail -> "PlacementID: ${event.view.query?.placementId}\nUnable to display content!\n${event.throwable?.message}"
        is BannerLoadContentSuccess -> "PlacementID: ${event.view.query?.placementId}\nBanner successfully displayed!"
        is BannerLoadDataFail -> "Unable to load data from server!\n${event.throwable?.message}"
        is BannerLoadDataSuccess -> "Data from server loaded"
        is BannerSentClickStatistic -> "PlacementID: ${event.view.query?.placementId}\nClick stats sent!"
        is BannerSentLoadStatistic -> "PlacementID: ${event.view.query?.placementId}\nLoad stats sent!"
        is BannerSentViewStatistic -> "PlacementID: ${event.view.query?.placementId}\nView stats sent!"
        is NoAdContent -> "PlacementID: ${event.view.query?.placementId}\nNo advertisement content!"
        is OnTap -> "PlacementID: ${event.view.query?.placementId} | Redirect url: ${event.redirectUrl} Tap on banner!"
    }

    Timber.tag("[DEBUG_MESSAGE]").i(message)
}
