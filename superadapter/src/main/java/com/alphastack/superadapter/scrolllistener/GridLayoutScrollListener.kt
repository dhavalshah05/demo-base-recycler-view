package com.alphastack.superadapter.scrolllistener

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridLayoutScrollListener(
    private val layoutManager: GridLayoutManager,
    private val adapter: RecyclerView.Adapter<*>
): BaseRecyclerViewScrollListener() {

    init {
        setVisibleItemThreshold(DEFAULT_VISIBLE_ITEM_THRESHOLD * layoutManager.spanCount)
    }

    override fun getTotalItemCount(): Int {
        return adapter.itemCount
    }

    override fun getLastVisibleItemPosition(): Int {
        return layoutManager.findLastVisibleItemPosition()
    }

}