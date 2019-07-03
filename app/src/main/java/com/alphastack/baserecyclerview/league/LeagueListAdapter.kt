package com.alphastack.baserecyclerview.league

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alphastack.baserecyclerview.BaseRecyclerViewAdapter
import com.alphastack.baserecyclerview.BaseViewHolder
import com.alphastack.baserecyclerview.R
import com.alphastack.baserecyclerview.model.League
import kotlinx.android.synthetic.main.league_list_item.view.*

class LeagueListAdapter: BaseRecyclerViewAdapter<League, LeagueListAdapter.LeagueListItemViewHolder>() {

    override fun getViewHolder(inflater: LayoutInflater, parent: ViewGroup): LeagueListItemViewHolder {
        val view = inflater.inflate(R.layout.league_list_item, parent, false)
        return LeagueListItemViewHolder(view)
    }


    inner class LeagueListItemViewHolder(view: View): BaseViewHolder<League>(view) {
        override fun bind(item: League) {
            itemView.textViewLeagueName.text = item.name
        }
    }
}