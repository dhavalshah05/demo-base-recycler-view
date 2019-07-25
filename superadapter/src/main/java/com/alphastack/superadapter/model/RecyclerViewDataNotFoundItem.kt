package com.alphastack.superadapter.model

data class RecyclerViewDataNotFoundItem(var message: String? = null) : RecyclerViewItem {
    override val itemId: String
        get() = ""
}