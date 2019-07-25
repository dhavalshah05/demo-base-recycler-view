package com.alphastack.superadapter.viewholder

import android.view.View
import com.alphastack.superadapter.model.RecyclerViewDataNotFoundItem
import kotlinx.android.synthetic.main.recycler_view_data_not_found_item.view.*

class DataNotFoundViewHolder(view: View) : BaseViewHolder<RecyclerViewDataNotFoundItem>(view) {
    override fun bind(item: RecyclerViewDataNotFoundItem) {
        itemView.textViewDataNotFound.text = item.message
    }
}