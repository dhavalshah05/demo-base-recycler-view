package com.alphastack.superadapter.scrolllistener

import androidx.recyclerview.widget.LinearLayoutManager
import com.alphastack.superadapter.BaseRecyclerViewAdapter

class LinearLayoutScrollListener(
        private val layoutManager: LinearLayoutManager,
        private val adapter: BaseRecyclerViewAdapter<*, *>
): BaseRecyclerViewScrollListener() {

    init {
        setVisibleItemThreshold(DEFAULT_VISIBLE_ITEM_THRESHOLD)
    }

    override fun getTotalItemCount(): Int {
        return adapter.getItems().size
    }

    override fun getLastVisibleItemPosition(): Int {
        return layoutManager.findLastVisibleItemPosition()
    }

}