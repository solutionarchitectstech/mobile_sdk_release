/*
 * Copyright © Gusev Andrew, Emelianov Andrew, Spinov Dmitry [collectively referred as the Authors], 2017-2022 - All Rights Reserved
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
import tech.solutionarchitects.advertisingsdk.core.model.Size
import tech.solutionarchitects.advertisingsdk.listener.*
import tech.solutionarchitects.advertisingsdk.types.CloseButtonType
import tech.solutionarchitects.testapplication.databinding.RecyclerViewItemBinding

class BannerItemHolder(private val binding: RecyclerViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Item) {
        when (item) {
            is BannerItem -> bindBannerView(item)
            EmptyItem -> bindEmptyView(item)
        }
    }

    private fun bindEmptyView(item: Item) {
        binding.textView.visibility = View.INVISIBLE
        binding.bannerView.visibility = View.INVISIBLE
    }

    private fun bindBannerView(item: BannerItem) {
        binding.textView.visibility = View.VISIBLE
        binding.bannerView.visibility = View.VISIBLE

        binding.textView.text = "placementID: ${item.placementID}"
        binding.bannerView.load(
            placementId = item.placementID.toString(),
            sizes = listOf(Size(width = 300, height = 150)),
            closeButtonType = CloseButtonType.None
        ) { event ->
            when (event) {
                is LoadDataSuccess -> {
                    println("LoadDataSuccess: ${event.placementId}")
                }

                is LoadDataFail -> {
                    println("LoadDataFail: ${event.throwable}")
                }

                is LoadContentSuccess -> {
                    println("LoadContentSuccess: ${event.placementId}")
                }

                is LoadContentFail -> {
                    println("LoadContentFail: ${event.throwable}")
                }
                is CloseButtonClick -> {
                    println("CloseButtonClick: ${event.placementId}")
                }
            }
        }
    }

}