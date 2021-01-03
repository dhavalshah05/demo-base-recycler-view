package com.alphastack.superadapter

import androidx.recyclerview.widget.DiffUtil
import com.alphastack.superadapter.model.RecyclerViewItem

internal class RecyclerViewItemCallBack : DiffUtil.ItemCallback<RecyclerViewItem>() {
    override fun areItemsTheSame(oldItem: RecyclerViewItem, newItem: RecyclerViewItem): Boolean {
        return oldItem.isSameItem(newItem)
    }

    override fun areContentsTheSame(oldItem: RecyclerViewItem, newItem: RecyclerViewItem): Boolean {
        return oldItem.isSameContent(newItem)
    }
}