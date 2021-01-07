package com.alphastack.baserecyclerview.model

import com.alphastack.superadapter.model.RecyclerViewItem

data class League(var id: Long? = null, var name: String? = null) : RecyclerViewItem {
    override val itemId: String
        get() = id!!.toString()
}