package com.alphastack.superadapter.scrolllistener

import androidx.recyclerview.widget.GridLayoutManager
import com.alphastack.superadapter.BaseRecyclerViewAdapter

class GridLayoutScrollListener(
    private val layoutManager: GridLayoutManager,
    private val adapter: BaseRecyclerViewAdapter<*, *>
): BaseRecyclerViewScrollListener() {

    init {
        setVisibleItemThreshold(DEFAULT_VISIBLE_ITEM_THRESHOLD * layoutManager.spanCount)
    }

    override fun getTotalItemCount(): Int {
        return adapter.getItems().size
    }

    override fun getLastVisibleItemPosition(): Int {
        return layoutManager.findLastVisibleItemPosition()
    }

}