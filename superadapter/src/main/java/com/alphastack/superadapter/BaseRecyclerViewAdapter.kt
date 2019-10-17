package com.alphastack.superadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alphastack.superadapter.model.RecyclerViewItem
import com.alphastack.superadapter.viewholder.BaseViewHolder
import java.util.*

abstract class BaseRecyclerViewAdapter<ItemType : RecyclerViewItem, VH : BaseViewHolder<ItemType>> :
    RecyclerView.Adapter<BaseViewHolder<*>>() {


    /**
     * Holds items.
     */
    private val items = mutableListOf<RecyclerViewItem>()


    private var recyclerViewHeight: Int = 0
    private var recyclerViewWidth: Int = 0

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerViewHeight = recyclerView.height
        this.recyclerViewWidth = recyclerView.width
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
        return getViewHolder(LayoutInflater.from(parent.context), parent, viewType)
    }

    /**
     *
     */
    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val item = items[position] as ItemType
        val viewHolder = holder as VH
        aboutToBindViewHolder(viewHolder, position)
        viewHolder.bind(item)
    }

    /**
     *
     */
    fun replaceItems(items: List<RecyclerViewItem>) {
        clearData()
        this.items.addAll(items)
        notifyItemRangeChanged(0, this.items.size)
    }

    /**
     *
     */
    fun appendItems(items: List<RecyclerViewItem>) {
        val startIndex = itemCount
        this.items.addAll(startIndex, items)
        notifyItemRangeInserted(startIndex, items.size)
    }


    /**
     *
     */
    fun appendItems(index: Int, items: List<RecyclerViewItem>) {
        this.items.addAll(index, items)
        notifyItemRangeInserted(index, items.size)
    }


    /**
     *
     */
    @Suppress("MemberVisibilityCanBePrivate")
    fun clearData() {
        //val previousItemListSize = this.items.size
        this.items.clear()
        notifyDataSetChanged()
        //notifyItemRangeRemoved(0, previousItemListSize)
    }

    /**
     *
     */
    fun addItem(item: RecyclerViewItem) {
        val indexToInsert = itemCount
        this.items.add(indexToInsert, item)
        this.notifyItemInserted(indexToInsert)
    }

    /**
     *
     */
    fun removeItem(item: RecyclerViewItem) {
        val index = getItemIndex(item)
        if (index != -1) {
            this.items.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    /**
     *
     */
    fun updateItem(item: RecyclerViewItem) {
        val index = getItemIndex(item)
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
        val items = mutableListOf<ItemType>()
        this.items.map { items.add(it as ItemType) }
        return Collections.unmodifiableList(items)
    }

    /**
     *
     */
    @Suppress("UNCHECKED_CAST")
    fun getItemById(id: String): ItemType? {
        var item: ItemType? = null

        for (obj in items) {
            if (obj.itemId == id) {
                item = obj as ItemType
                break
            }
        }

        return item
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
        return this.items[index] as ItemType
    }

    /**
     * If item is available in list, this will return index of that item.
     * Else it will return -1.
     */
    private fun getItemIndex(item: RecyclerViewItem): Int {
        var resultIndex = -1

        for ((index, obj) in items.withIndex()) {
            if (obj.itemId == item.itemId) {
                resultIndex = index
                break
            }
        }

        return resultIndex
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