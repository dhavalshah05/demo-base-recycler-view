package com.alphastack.superadapter.model

interface RecyclerViewItem {
    val itemId: String

    fun isSameItemAs(newItem: RecyclerViewItem): Boolean {
        return itemId == newItem.itemId
    }

    fun isSameContentAs(newItem: RecyclerViewItem): Boolean {
        return this == newItem
    }

}