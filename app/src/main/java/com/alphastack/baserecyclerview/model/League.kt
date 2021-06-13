package com.alphastack.baserecyclerview.model

import com.alphastack.superadapter.model.RecyclerViewItem

data class League(val id: Long, val name: String) : RecyclerViewItem {
    override val itemId: String
        get() = id.toString()
}