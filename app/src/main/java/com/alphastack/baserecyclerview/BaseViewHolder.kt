package com.alphastack.baserecyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alphastack.baserecyclerview.model.RecyclerViewItem

abstract class BaseViewHolder<ItemType: RecyclerViewItem>(view: View): RecyclerView.ViewHolder(view) {
    abstract fun bind(item: ItemType)
}