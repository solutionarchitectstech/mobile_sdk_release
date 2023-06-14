package tech.solutionarchitects.testapplication.activity.recycler_native

import tech.solutionarchitects.advertisingsdk.api.AdvertisingSDKExperimental
import tech.solutionarchitects.advertisingsdk.api.feature.nativebanner.NativeBannerView

sealed interface Item {
    companion object {
        const val NATIVE_BANNER_TYPE = 1
        const val EMPTY_ITEM_TYPE = 2
    }

    data class NativeBannerItem @OptIn(AdvertisingSDKExperimental::class) constructor(
        val nativeBannerView: NativeBannerView,
        val refresh: Int? = null
    ) :
        Item

    data class EmptyItem(val order: Int) : Item
}
