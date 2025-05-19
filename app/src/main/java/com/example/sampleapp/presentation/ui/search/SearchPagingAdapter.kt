package com.example.sampleapp.presentation.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapp.databinding.ItemSearchBinding
import com.example.sampleapp.domain.model.Search

class SearchPagingAdapter(
    private val onCheckedEvent: ((Boolean, Search) -> Unit)? = null
) : PagingDataAdapter<Search, RecyclerView.ViewHolder>(
    SearchDiffCallback()
) {


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchViewHolder -> getItem(position)?.let { holder.bind(it) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SearchViewHolder(
            ItemSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onCheckedEvent = onCheckedEvent
        )
    }


    class SearchViewHolder(
        private val binding: ItemSearchBinding,
        private val onCheckedEvent: ((Boolean, Search) -> Unit)? = null
    ) : RecyclerView.ViewHolder(binding.root) {

        private var viewData: Search? = null

        init {
            binding.tgSaved.apply {
                visibility = if (onCheckedEvent == null) View.GONE else View.VISIBLE

                itemView.setOnClickListener {
                    onCheckedEvent?.let { event ->
                        isChecked = isChecked.not()

                        viewData?.let { data ->
                            event(isChecked, data)
                        }
                    }
                }
            }
        }

        fun bind(data: Search) = with(binding) {
            viewData = data
            info = data
            executePendingBindings()
        }

    }

}


private class SearchDiffCallback : DiffUtil.ItemCallback<Search>() {
    override fun areItemsTheSame(oldItem: Search, newItem: Search): Boolean {
        return oldItem.getKey() == newItem.getKey()
    }

    override fun areContentsTheSame(oldItem: Search, newItem: Search): Boolean {
        return oldItem == newItem
    }

}