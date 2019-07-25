package com.alphastack.superadapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alphastack.superadapter.model.RecyclerViewItem

abstract class BaseViewHolder<ItemType : RecyclerViewItem>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(item: ItemType)
}