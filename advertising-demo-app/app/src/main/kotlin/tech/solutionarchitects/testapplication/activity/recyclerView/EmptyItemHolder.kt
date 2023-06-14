package tech.solutionarchitects.testapplication.activity.recyclerView

import androidx.recyclerview.widget.RecyclerView
import tech.solutionarchitects.testapplication.databinding.EmptyItemBinding

class EmptyItemHolder(private val binding: EmptyItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Item.EmptyItem) {
        bindEmptyView(item)
    }

    private fun bindEmptyView(item: Item.EmptyItem) {
        binding.textView.text = "#: ${item.order}"
    }
}
