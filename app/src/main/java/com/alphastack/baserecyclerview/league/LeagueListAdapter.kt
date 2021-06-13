package com.alphastack.baserecyclerview.league

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alphastack.baserecyclerview.R
import com.alphastack.baserecyclerview.model.League
import com.alphastack.superadapter.BaseRecyclerViewAdapter
import com.alphastack.superadapter.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.league_list_item.view.*

class LeagueListAdapter :
    BaseRecyclerViewAdapter<League, LeagueListAdapter.LeagueListItemViewHolder>() {

    private var onItemClick: ((League) -> Unit)? = null
    private var onItemLongClick: ((League) -> Unit)? = null

    fun onItemClicked(itemClick: (League) -> Unit) {
        this.onItemClick = itemClick
    }

    fun onItemLongClicked(itemLongClick: (League) -> Unit) {
        this.onItemLongClick = itemLongClick
    }

    override fun getViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): LeagueListItemViewHolder {
        val view = inflater.inflate(R.layout.league_list_item, parent, false)
        return LeagueListItemViewHolder(view)
    }

    override fun aboutToBindViewHolder(viewHolder: LeagueListItemViewHolder, position: Int) {
        // Set margin top for first item.
        val lastItem = position == itemCount - 1
        val layoutParams = viewHolder.itemView.layoutParams as RecyclerView.LayoutParams
        layoutParams.marginStart = 30
        layoutParams.marginEnd = 30
        layoutParams.bottomMargin = 30
        if (position == 0) {
            layoutParams.topMargin = 30
        } else {
            layoutParams.topMargin = 0
        }
        viewHolder.itemView.layoutParams = layoutParams
    }


    inner class LeagueListItemViewHolder(view: View) : BaseViewHolder<League>(view) {

        private lateinit var item: League

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(item)
            }
            itemView.setOnLongClickListener {
                onItemLongClick?.invoke(item)
                true
            }
        }

        override fun bind(item: League) {
            this.item = item
            itemView.textViewLeagueName.text = item.name
        }
    }
}