package com.alphastack.superadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alphastack.superadapter.viewholder.BaseViewHolder
import java.util.*

abstract class BaseRecyclerViewAdapter<ItemType, VH : BaseViewHolder<ItemType>> :
    RecyclerView.Adapter<BaseViewHolder<ItemType>>() {

    /**
     * Holds items.
     */
    private val items = mutableListOf<ItemType>()

    /**
     *
     */
    override fun getItemCount(): Int {
        return items.size
    }

    /**
     *
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ItemType> {
        return getViewHolder(LayoutInflater.from(parent.context), parent, viewType)
    }

    /**
     *
     */
    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: BaseViewHolder<ItemType>, position: Int) {
        val item = items[position]
        val viewHolder = holder as VH
        aboutToBindViewHolder(viewHolder, position)
        viewHolder.bind(item)
    }

    /**
     *
     */
    fun replaceItems(items: List<ItemType>) {
        clearData()
        this.items.addAll(items)
        notifyItemRangeChanged(0, this.items.size)
    }

    /**
     *
     */
    fun appendItemsAtEnd(items: List<ItemType>) {
        val startIndex = itemCount
        this.items.addAll(startIndex, items)
        notifyItemRangeInserted(startIndex, items.size)
    }


    /**
     *
     */
    fun appendItemsAt(index: Int, items: List<ItemType>) {
        this.items.addAll(index, items)
        notifyItemRangeInserted(index, items.size)
    }


    /**
     *
     */
    @Suppress("MemberVisibilityCanBePrivate")
    fun clearData() {
        this.items.clear()
        notifyDataSetChanged()
    }

    /**
     *
     */
    fun addItem(item: ItemType) {
        val indexToInsert = itemCount
        this.items.add(indexToInsert, item)
        this.notifyItemInserted(indexToInsert)
    }

    /**
     *
     */
    fun removeItem(item: ItemType, predicate: (ItemType) -> Boolean) {
        val index = items.indexOfFirst(predicate)
        if (index != -1) {
            this.items.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    /**
     *
     */
    fun updateItem(item: ItemType, predicate: (ItemType) -> Boolean) {
        val index = items.indexOfFirst(predicate)
        if (index != -1) {
            this.items[index] = item
            notifyItemChanged(index)
        }
    }

    /**
     *
     */
    @Suppress("UNCHECKED_CAST")
    fun getItems(): List<ItemType> {
        return Collections.unmodifiableList(items)
    }

    /**
     * This will return item from index or return null.
     */
    @Suppress("MemberVisibilityCanBePrivate", "UNCHECKED_CAST")
    fun getItemByIndex(index: Int): ItemType? {
        // Return null if index is out of bound
        if (index < 0 || index > this.items.size - 1) {
            return null
        }
        return this.items[index]
    }


    /**
     * This method is called before binding item.
     * Override this method for setting margin for inflated view.
     */
    open fun aboutToBindViewHolder(viewHolder: VH, position: Int) {
        // No operation
    }

    /**
     *
     */
    abstract fun getViewHolder(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): VH

}