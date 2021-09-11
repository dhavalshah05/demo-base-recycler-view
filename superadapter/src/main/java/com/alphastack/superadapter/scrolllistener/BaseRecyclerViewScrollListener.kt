package com.alphastack.superadapter.scrolllistener

import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewScrollListener : RecyclerView.OnScrollListener() {

    companion object {
        const val INITIAL_PAGE = 1
        const val DEFAULT_VISIBLE_ITEM_THRESHOLD = 5
    }

    private var loading: Boolean = true

    private var currentPage: Int = 1

    private var previousItemsCount = 0

    private var visibleItemThreshold =
        DEFAULT_VISIBLE_ITEM_THRESHOLD

    private var loadMore: ((Int, Int, RecyclerView) -> Unit)? = null

    fun onLoadMore(loadMore: (page: Int, totalItemCount: Int, recyclerView: RecyclerView) -> Unit) {
        this.loadMore = loadMore
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val totalItemCount = getTotalItemCount()
        val lastVisibleItemPosition = getLastVisibleItemPosition()

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousItemsCount) {
            this.currentPage =
                INITIAL_PAGE
            this.previousItemsCount = totalItemCount
            if (totalItemCount == 0) {
                this.loading = true
            }
        }

        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading && (totalItemCount > previousItemsCount)) {
            this.loading = false
            this.previousItemsCount = totalItemCount
        }

        if (!loading && (lastVisibleItemPosition + visibleItemThreshold) > totalItemCount) {
            currentPage++
            this.loadMore?.invoke(currentPage, totalItemCount, recyclerView)
            loading = true
        }
    }

    fun resetState() {
        this.currentPage =
            INITIAL_PAGE
        this.previousItemsCount = 0
        this.loading = true
    }

    protected fun setVisibleItemThreshold(itemThreshold: Int) {
        this.visibleItemThreshold = itemThreshold
    }

    abstract fun getTotalItemCount(): Int

    abstract fun getLastVisibleItemPosition(): Int
}