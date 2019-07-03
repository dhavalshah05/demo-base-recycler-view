package com.alphastack.baserecyclerview

import android.view.View
import com.alphastack.baserecyclerview.model.DataNotFoundItem
import kotlinx.android.synthetic.main.recycler_view_data_not_found_item.view.*

class DataNotFoundViewHolder(view: View): BaseViewHolder<DataNotFoundItem>(view) {
    override fun bind(item: DataNotFoundItem) {
        itemView.textViewDataNotFound.text = item.message
    }
}