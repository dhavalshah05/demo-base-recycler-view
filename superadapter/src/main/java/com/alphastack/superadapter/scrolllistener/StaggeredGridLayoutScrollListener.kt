package com.alphastack.superadapter.scrolllistener

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class StaggeredGridLayoutScrollListener(
    private val layoutManager: StaggeredGridLayoutManager,
    private val adapter: RecyclerView.Adapter<*>
): BaseRecyclerViewScrollListener() {

    init {
        setVisibleItemThreshold(DEFAULT_VISIBLE_ITEM_THRESHOLD * layoutManager.spanCount)
    }

    override fun getTotalItemCount(): Int {
        return adapter.itemCount
    }

    override fun getLastVisibleItemPosition(): Int {
        val lastVisibleItemPositions = layoutManager.findLastVisibleItemPositions(null)

        var maxSize = 0
        for(position in lastVisibleItemPositions) {
            if(position == 0) {
                maxSize = lastVisibleItemPositions[position]
            } else if(lastVisibleItemPositions[position] > maxSize) {
                maxSize = lastVisibleItemPositions[position]
            }
        }

        return maxSize
    }

}