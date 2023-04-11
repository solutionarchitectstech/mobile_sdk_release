/*
 * Copyright © Gusev Andrew, Emelianov Andrew, Spinov Dmitry [collectively referred as the Authors], 2017 - All Rights Reserved
 * [NOTICE: All information contained herein is, and remains the property of the Authors.](notice: All information contained herein is, and remains the property of the Authors.)
 * The intellectual and technical concepts contained herein are proprietary to the Authors
 * and may be covered by any existing patents of any country in the world, patents in
 * process, and are protected by trade secret or copyright law.
 *
 * Dissemination of this information or reproduction of this material is strictly forbidden unless prior
 * written permission is obtained from the Authors. Access to the source code contained herein is hereby forbidden
 * to anyone except persons (natural person, corporate or unincorporated body, whether or not having a separate
 * legal personality, and that person’s personal representatives, and successors)
 * the Authors have granted permission and who have executed Confidentiality and Non-disclosure agreements
 * explicitly covering such access.
 *
 * The copyright notice above does not provide evidence of any actual or intended publication or disclosure
 * of this source code, which in
 */

package tech.solutionarchitects.testapplication.activity.recyclerView

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import tech.solutionarchitects.advertisingsdk.api.common.BannerCloseButtonClick
import tech.solutionarchitects.advertisingsdk.api.common.BannerLoadContentFail
import tech.solutionarchitects.advertisingsdk.api.common.BannerLoadContentSuccess
import tech.solutionarchitects.advertisingsdk.api.common.BannerLoadDataFail
import tech.solutionarchitects.advertisingsdk.api.common.BannerLoadDataSuccess
import tech.solutionarchitects.advertisingsdk.api.common.Size
import tech.solutionarchitects.advertisingsdk.api.feature.banner.BannerAdvertisementQuery
import tech.solutionarchitects.advertisingsdk.api.CloseButtonType
import tech.solutionarchitects.testapplication.databinding.RecyclerViewItemBinding

class BannerItemHolder(private val binding: RecyclerViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Item) {
        when (item) {
            is BannerItem -> bindBannerView(item)
            is EmptyItem -> bindEmptyView(item)
        }
    }

    private fun bindEmptyView(item: EmptyItem) {
        binding.bannerView.visibility = View.INVISIBLE
        binding.textView.text = "#: ${item.order}"
    }

    private fun bindBannerView(item: BannerItem) {
        binding.bannerView.visibility = View.VISIBLE

        binding.textView.text = "#: ${item.order}"
        binding.bannerView.load(
            query = BannerAdvertisementQuery(
                placementId = item.placementID.toString(),
                sizes = listOf(Size(width = 1024, height = 768)),
            ),
            closeButtonType = CloseButtonType.None
        ) { event ->
            when (event) {
                is BannerLoadDataSuccess -> {
                    println("BannerLoadDataSuccess: ${event.placementId}")
                }
                is BannerLoadDataFail -> {
                    println("BannerLoadDataFail: ${event.throwable}")
                }
                is BannerLoadContentSuccess -> {
                    println("BannerLoadContentSuccess: ${event.placementId}")
                }
                is BannerLoadContentFail -> {
                    println("BannerLoadContentFail: ${event.throwable}")
                }
                is BannerCloseButtonClick -> {
                    println("BannerCloseButtonClick: ${event.placementId}")
                }
                else -> {}
            }
        }
    }

}