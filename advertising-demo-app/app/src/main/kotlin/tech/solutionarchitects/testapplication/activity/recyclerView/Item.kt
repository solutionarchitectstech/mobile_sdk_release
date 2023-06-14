package tech.solutionarchitects.testapplication.activity.recyclerView

import tech.solutionarchitects.advertisingsdk.api.feature.banner.BannerView

sealed interface Item {
    companion object {
        const val BANNER_TYPE = 1
        const val EMPTY_ITEM_TYPE = 3
    }

    data class BannerItem(val bannerView: BannerView, val refresh: Int? = null) : Item

    data class EmptyItem(val order: Int) : Item
}
