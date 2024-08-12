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

package tech.solutionarchitects.testapplication.activity

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tech.solutionarchitects.advertisingsdk.creative.presentation.CreativeEventListener
import tech.solutionarchitects.advertisingsdk.creative.domain.model.Size
import tech.solutionarchitects.advertisingsdk.creative.presentation.Creative
import tech.solutionarchitects.advertisingsdk.creative.domain.model.CreativeQuery
import tech.solutionarchitects.advertisingsdk.creative.presentation.CreativeView
import tech.solutionarchitects.testapplication.R
import tech.solutionarchitects.testapplication.databinding.ActivityCreativeRecyclerViewBinding
import tech.solutionarchitects.testapplication.databinding.RecyclerCreativeViewItemBinding
import tech.solutionarchitects.testapplication.hideMessage
import tech.solutionarchitects.testapplication.log
import tech.solutionarchitects.testapplication.showMessage

class CreativeRecyclerViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreativeRecyclerViewBinding

    private val placementIds = arrayOf(
        "HTML_BANNER",
        "VAST_Inline_Simple",
        "IMAGE_BANNER",
        "VAST_Wrapper_Simple",
        "HTML_BANNER",
        "VAST_Wrapper_Compound",
        "IMAGE_BANNER",
        "VAST_Wrapper_Chain_Less_5",
        "HTML_BANNER",
        "VAST_Wrapper_Chain_Greater_5",
        "IMAGE_BANNER",
        "VAST_Wrapper_Chain_Loop",
        "HTML_BANNER",
        "IMAGE_BANNER"
    )

    private val items: List<CreativeQuery>
        get() {
            return mutableListOf<CreativeQuery>().also { list ->
                placementIds.forEach { placementId ->
                    list.add(
                        CreativeQuery(
                            placementId = placementId,
                            sizes = listOf(Size(width = 260, height = 106)),
                        )
                    )
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreativeRecyclerViewBinding.inflate(layoutInflater)

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        binding.recyclerView.adapter = CreativeAdapter(
            lifecycle = lifecycle,
            items = items,
            listener = object : CreativeEventListener {
                override fun onLoadDataSuccess(creativeView: CreativeView) {
                    val placementId = creativeView.query?.placementId
                    log(Log.DEBUG, "onLoadDataSuccess[${placementId}]")

                    val errorLabel = creativeView.tag as? TextView
                    errorLabel?.let {
                        hideMessage(it)
                    }
                }

                override fun onLoadDataFail(creativeView: CreativeView, throwable: Throwable?) {
                    val placementId = creativeView.query?.placementId
                    val msg = "onLoadDataFail[${placementId}]: ${throwable?.message}"
                    log(Log.ERROR, msg)

                    val errorLabel = creativeView.tag as? TextView
                    errorLabel?.let {
                        showMessage(msg, it, creativeView, Color.RED)
                    }
                }

                override fun onLoadContentSuccess(creativeView: CreativeView) {
                    val placementId = creativeView.query?.placementId
                    log(Log.DEBUG, "onLoadContentSuccess[${placementId}]")

                    val errorLabel = creativeView.tag as? TextView
                    errorLabel?.let {
                        hideMessage(it)
                    }
                }

                override fun onLoadContentFail(creativeView: CreativeView, throwable: Throwable?) {
                    val placementId = creativeView.query?.placementId
                    val msg = "onLoadContentFail[${placementId}]: ${throwable?.message}"
                    log(Log.ERROR, msg)

                    val errorLabel = creativeView.tag as? TextView
                    errorLabel?.let {
                        showMessage(msg, it, creativeView, Color.RED)
                    }
                }

                override fun onNoAdContent(creativeView: CreativeView) {
                    val placementId = creativeView.query?.placementId
                    val msg = "onNoAdContent[${placementId}]"
                    log(Log.WARN, msg)

                    val errorLabel = creativeView.tag as? TextView
                    errorLabel?.let {
                        showMessage(msg, it, creativeView, Color.RED)
                    }
                }

                override fun onClose(creativeView: CreativeView) {
                    val placementId = creativeView.query?.placementId
                    log(Log.DEBUG, "onClose[${placementId}]")

                    val errorLabel = creativeView.tag as? TextView
                    errorLabel?.let {
                        hideMessage(it)
                    }
                }
            }
        )
    }
}

private class CreativeAdapter(
    private val lifecycle: Lifecycle,
    private val items: List<CreativeQuery>,
    private val listener: CreativeEventListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var context: Context
    private val creatives = mutableMapOf<Int, Creative>()
    private val creativeViews = mutableMapOf<Int, CreativeView>()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    private class CreativeHolder(view: View) : RecyclerView.ViewHolder(view) {
        val labelTextView: TextView
        val containerLayout: ViewGroup

        init {
            labelTextView = view.findViewById(R.id.labelTextView)
            containerLayout = view.findViewById(R.id.containerLayout)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RecyclerCreativeViewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return CreativeHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        var creativeView = creativeViews.get(position)
        if (creativeView == null) {
            creativeView = CreativeView(context).apply { query = item }
            creativeViews.put(position, creativeView)

            // Here is the trick to create and associate errorLabel with necessary creativeView.
            // We store it in the `creativeView.tag` ;)
            creativeView.tag = TextView(context)
        }
        if (creativeView.parent != null) {
            (creativeView.parent as ViewGroup).removeView(creativeView)
        }
        val creativeHolder = (holder as CreativeHolder)
        creativeHolder.containerLayout.removeAllViews()
        creativeHolder.containerLayout.addView(
            creativeView,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        creativeHolder.labelTextView.text = item.placementId

        var creative = creatives.get(position)
        if (creative == null) {
            creative = Creative(
                lifecycle,
                creativeView = creativeView,
                listener = listener
            )
            creatives.put(position, creative)
            creative.load()
        }
    }

    override fun getItemCount(): Int = items.size
}
