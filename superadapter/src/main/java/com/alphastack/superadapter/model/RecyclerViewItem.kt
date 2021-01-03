package com.alphastack.superadapter.model

interface RecyclerViewItem {
    val itemId: String

    fun isSameItem(newItem: RecyclerViewItem): Boolean

    fun isSameContent(newItem: RecyclerViewItem): Boolean
}