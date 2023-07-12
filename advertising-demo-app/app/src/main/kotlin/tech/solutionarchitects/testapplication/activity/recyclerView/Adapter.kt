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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import tech.solutionarchitects.advertisingsdk.api.AdvertisingSDKExperimental
import tech.solutionarchitects.advertisingsdk.api.BannerCreativeHolder
import tech.solutionarchitects.advertisingsdk.api.common.BannerCreativeEvent
import tech.solutionarchitects.advertisingsdk.api.common.TechAdvertisingListener
import tech.solutionarchitects.testapplication.databinding.EmptyItemBinding
import tech.solutionarchitects.testapplication.databinding.RecyclerViewItemBinding

class Adapter(
    private val lifecycle: Lifecycle,
    private val dataSet: List<Item>,
    private val listener: TechAdvertisingListener<BannerCreativeEvent>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Item.EMPTY_ITEM_TYPE -> {
                EmptyItemHolder(
                    binding = EmptyItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            Item.BANNER_TYPE -> {
                BannerCreativeHolder(
                    view = RecyclerViewItemBinding
                        .inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        ).root,
                    lifecycle = lifecycle,
                    listener = listener
                )
            }

            else -> {
                throw Exception("Undefined view type")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = dataSet[position]) {
            is Item.EmptyItem -> (holder as EmptyItemHolder).bind(item)
            is Item.BannerItem -> {
                val layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                val banner = item.bannerView
                banner.layoutParams = layoutParams
                val container = (holder as BannerCreativeHolder).itemView as ViewGroup
                holder.apply {
                    (this.itemView as ViewGroup).apply {
                        if (this.childCount > 0) this.removeAllViews()
                    }
                }
                if (banner.parent != null) {
                    (banner.parent as ViewGroup).removeView(banner)
                }
                container.addView(banner, layoutParams)

                holder.loadIfNeeded(bannerView = banner, refresh = item.refresh)
            }
        }
    }


    override fun getItemCount(): Int = dataSet.size

    override fun getItemViewType(position: Int): Int {
        return when (dataSet[position]) {
            is Item.BannerItem -> Item.BANNER_TYPE
            is Item.EmptyItem -> Item.EMPTY_ITEM_TYPE
        }
    }
}


