package com.alphastack.baserecyclerview.league

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.alphastack.baserecyclerview.model.League
import com.alphastack.superadapter.BaseRecyclerView
import com.alphastack.superadapter.BaseRecyclerViewAdapter
import com.alphastack.superadapter.scrolllistener.BaseRecyclerViewScrollListener
import com.alphastack.superadapter.scrolllistener.LinearLayoutScrollListener
import com.alphastack.superadapter.viewholder.BaseViewHolder

class LeagueListRecyclerView(
        layoutInflater: LayoutInflater,
        parent: ViewGroup?
) : BaseRecyclerView<League>(layoutInflater, parent) {

    private val adapter = LeagueListAdapter()
    private val layoutManager = LinearLayoutManager(getContext())
    private val scrollListener = LinearLayoutScrollListener(layoutManager, adapter)

    init {
        getRecyclerView().layoutManager = layoutManager
        getRecyclerView().adapter = adapter
        getRecyclerView().addOnScrollListener(scrollListener)

        scrollListener.onLoadMore { page, _, _ ->
            println("ON_LOAD_MORE $page")
        }
    }

    override fun getAdapter(): BaseRecyclerViewAdapter<League, out BaseViewHolder<League>> {
        return adapter
    }

    override fun getDataNotFoundMessage(): String {
        return "Data Not Found From LeagueListRecyclerView"
    }

    override fun getScrollListener(): BaseRecyclerViewScrollListener? {
        return scrollListener
    }
}