package com.alphastack.superadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.alphastack.superadapter.model.RecyclerViewItem
import com.alphastack.superadapter.viewholder.BaseViewHolder
import java.util.*

abstract class BaseRecyclerViewAdapter<ItemType : RecyclerViewItem, VH : BaseViewHolder<ItemType>> :
        ListAdapter<RecyclerViewItem, BaseViewHolder<*>>(RecyclerViewItemCallBack()) {

    /**
     * Holds items.
     */
    private val items = mutableListOf<RecyclerViewItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return getViewHolder(LayoutInflater.from(parent.context), parent, viewType)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val item = getItem(position) as ItemType
        val viewHolder = holder as VH
        aboutToBindViewHolder(viewHolder, position)
        viewHolder.bind(item)
    }

    fun clearData() {
        items.clear()
        submitList(items.toList())
    }

    fun replaceItems(newItems: List<RecyclerViewItem>) {
        items.clear()
        items.addAll(newItems)
        submitList(items.toList())
    }

    fun appendItemsAtEnd(newItems: List<RecyclerViewItem>) {
        items.addAll(newItems)
        submitList(items.toList())
    }

    fun appendItemsAtStart(newItems: List<RecyclerViewItem>) {
        items.addAll(0, newItems)
        submitList(items.toList())
    }

    fun addItem(item: RecyclerViewItem) {
        items.add(item)
        submitList(items.toList())
    }

    fun removeItem(item: RecyclerViewItem) {
        val index = getItemIndex(item)
        if (index != -1) {
            items.removeAt(index)
            submitList(items.toList())
        }
    }

    fun updateItem(item: RecyclerViewItem) {
        val index = getItemIndex(item)
        if (index != -1) {
            items[index] = item
            submitList(items.toList())
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
        return items[index] as ItemType
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