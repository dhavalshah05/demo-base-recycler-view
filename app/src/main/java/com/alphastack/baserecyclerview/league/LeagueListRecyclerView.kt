package com.alphastack.baserecyclerview.league

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.alphastack.baserecyclerview.model.League
import com.alphastack.superadapter.BaseRecyclerView
import com.alphastack.superadapter.BaseRecyclerViewAdapter
import com.alphastack.superadapter.viewholder.BaseViewHolder

class LeagueListRecyclerView(
        layoutInflater: LayoutInflater,
        parent: ViewGroup?
) : BaseRecyclerView<League>(layoutInflater, parent) {

    private val adapter = LeagueListAdapter()
    private val layoutManager = LinearLayoutManager(getContext())

    init {
        getRecyclerView().layoutManager = layoutManager
        getRecyclerView().adapter = adapter
    }

    override fun getAdapter(): BaseRecyclerViewAdapter<League, out BaseViewHolder<League>> {
        return adapter
    }

    override fun getDataNotFoundMessage(): String {
        return "Data Not Found From LeagueListRecyclerView"
    }

}