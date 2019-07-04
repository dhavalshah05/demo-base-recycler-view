package com.alphastack.baserecyclerview.league

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alphastack.baserecyclerview.BaseRecyclerViewAdapter
import com.alphastack.baserecyclerview.BaseViewHolder
import com.alphastack.baserecyclerview.R
import com.alphastack.baserecyclerview.model.League
import kotlinx.android.synthetic.main.league_list_item.view.*

class LeagueListAdapter : BaseRecyclerViewAdapter<League, LeagueListAdapter.LeagueListItemViewHolder>() {

    override fun getViewHolder(inflater: LayoutInflater, parent: ViewGroup): LeagueListItemViewHolder {
        val view = inflater.inflate(R.layout.league_list_item, parent, false)
        return LeagueListItemViewHolder(view)
    }

    override fun aboutToBindViewHolder(viewHolder: LeagueListItemViewHolder, position: Int) {
        // Set margin bottom for last item
        val lastItem = position == itemCount - 1
        val layoutParams = viewHolder.itemView.layoutParams as RecyclerView.LayoutParams
        layoutParams.marginStart = 30
        layoutParams.marginEnd = 30
        layoutParams.topMargin = 30
        if (lastItem) {
            layoutParams.bottomMargin = 30
        } else {
            layoutParams.bottomMargin = 0
        }
        viewHolder.itemView.layoutParams = layoutParams
    }


    inner class LeagueListItemViewHolder(view: View) : BaseViewHolder<League>(view) {
        override fun bind(item: League) {
            itemView.textViewLeagueName.text = item.name
        }
    }
}