package tech.solutionarchitects.testapplication.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tech.solutionarchitects.advertisingsdk.api.feature.creative.CreativeEventListener
import tech.solutionarchitects.advertisingsdk.api.common.Size
import tech.solutionarchitects.advertisingsdk.api.feature.creative.Creative
import tech.solutionarchitects.advertisingsdk.api.feature.creative.CreativeQuery
import tech.solutionarchitects.advertisingsdk.api.feature.creative.CreativeView
import tech.solutionarchitects.testapplication.R
import tech.solutionarchitects.testapplication.databinding.ActivityCreativeRecyclerViewBinding
import tech.solutionarchitects.testapplication.databinding.RecyclerCreativeViewItemBinding
import timber.log.Timber

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
                }

                override fun onLoadDataFail(creativeView: CreativeView, throwable: Throwable?) {
                    val placementId = creativeView.query?.placementId
                    log(Log.ERROR, "onLoadDataFail[${placementId}]: ${throwable?.message}")
                }

                override fun onLoadContentSuccess(creativeView: CreativeView) {
                    val placementId = creativeView.query?.placementId
                    log(Log.DEBUG, "onLoadContentSuccess[${placementId}]")
                }

                override fun onLoadContentFail(creativeView: CreativeView, throwable: Throwable?) {
                    val placementId = creativeView.query?.placementId
                    log(Log.ERROR, "onLoadContentFail[${placementId}]: ${throwable?.message}")
                }

                override fun onNoAdContent(creativeView: CreativeView) {
                    val placementId = creativeView.query?.placementId
                    log(Log.WARN, "onNoAdContent[${placementId}]")
                }

                override fun onClose(creativeView: CreativeView) {
                    val placementId = creativeView.query?.placementId
                    log(Log.DEBUG, "onClose[${placementId}]")
                }
            }
        )
    }

    private fun log(priority: Int, message: String) {
        Timber.log(priority, message)
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
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
