package com.alphastack.baserecyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alphastack.baserecyclerview.model.BugTrackerObject

abstract class BaseViewHolder<ItemType: BugTrackerObject>(view: View): RecyclerView.ViewHolder(view) {
    abstract fun bind(item: ItemType)
}