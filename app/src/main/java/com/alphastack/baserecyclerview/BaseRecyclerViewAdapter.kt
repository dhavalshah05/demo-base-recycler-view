package com.alphastack.baserecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alphastack.baserecyclerview.model.DataNotFoundItem
import com.alphastack.baserecyclerview.model.RecyclerViewItem

abstract class BaseRecyclerViewAdapter<ItemType : RecyclerViewItem, VH : BaseViewHolder<ItemType>> :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    companion object {
        private const val ITEM_TYPE_DATA_NOT_FOUND = 111
        private const val ITEM_TYPE_DATA = 222
    }

    /**
     * Holds items.
     */
    private val items = mutableListOf<RecyclerViewItem>()

    /**
     *
     */
    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is DataNotFoundItem -> ITEM_TYPE_DATA_NOT_FOUND
            else -> ITEM_TYPE_DATA
        }
    }

    /**
     *
     */
    override fun getItemCount(): Int {
        return items.size
    }

    /**
     *
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val viewHolder: BaseViewHolder<*>

        if (viewType == ITEM_TYPE_DATA_NOT_FOUND) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_data_not_found_item, parent, false)
            viewHolder = DataNotFoundViewHolder(view)
        } else {
            viewHolder = getViewHolder(LayoutInflater.from(parent.context), parent)
        }

        return viewHolder
    }

    /**
     *
     */
    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val itemType = getItemViewType(position)

        if (itemType == ITEM_TYPE_DATA) {
            val item = items[position] as ItemType
            val viewHolder = holder as VH
            viewHolder.bind(item)
        } else if (itemType == ITEM_TYPE_DATA_NOT_FOUND && holder is DataNotFoundViewHolder) {
            val item = items[position] as DataNotFoundItem
            holder.bind(item)
        }

    }

    /**
     *
     */
    fun showDataNotFoundMessage(message: String) {
        val item = DataNotFoundItem(message)
        this.items.clear()
        this.items.add(item)
        notifyDataSetChanged()
    }

    /**
     *
     */
    fun replaceData(items: List<RecyclerViewItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    /**
     *
     */
    abstract fun getViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH

}