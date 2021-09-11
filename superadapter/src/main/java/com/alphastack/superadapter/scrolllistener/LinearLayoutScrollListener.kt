package com.alphastack.superadapter.scrolllistener

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LinearLayoutScrollListener(
        private val layoutManager: LinearLayoutManager,
        private val adapter: RecyclerView.Adapter<*>
): BaseRecyclerViewScrollListener() {

    init {
        setVisibleItemThreshold(DEFAULT_VISIBLE_ITEM_THRESHOLD)
    }

    override fun getTotalItemCount(): Int {
        return adapter.itemCount
    }

    override fun getLastVisibleItemPosition(): Int {
        return layoutManager.findLastVisibleItemPosition()
    }

}