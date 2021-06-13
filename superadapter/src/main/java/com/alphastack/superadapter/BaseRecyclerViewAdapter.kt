package com.alphastack.superadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.alphastack.superadapter.model.RecyclerViewItem
import com.alphastack.superadapter.viewholder.BaseViewHolder
import java.util.*

abstract class BaseRecyclerViewAdapter<ItemType : RecyclerViewItem, VH : BaseViewHolder<ItemType>> :
    ListAdapter<RecyclerViewItem, BaseViewHolder<*>>(RecyclerViewItemCallBack()) {

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
        submitList(listOf())
    }

    fun replaceItems(newItems: List<RecyclerViewItem>) {
        submitList(newItems)
    }

    fun appendItemsAtEnd(newItems: List<RecyclerViewItem>) {
        val dataList = currentList.toMutableList()
        dataList.addAll(newItems)
        submitList(dataList.toList())
    }

    fun appendItemsAtStart(newItems: List<RecyclerViewItem>) {
        val dataList = currentList.toMutableList()
        dataList.addAll(0, newItems)
        submitList(dataList.toList())
    }

    fun addItem(item: RecyclerViewItem) {
        val dataList = currentList.toMutableList()
        dataList.add(item)
        submitList(dataList.toList())
    }

    fun removeItem(item: RecyclerViewItem) {
        val index = getItemIndex(item)
        if (index != -1) {
            val dataList = currentList.toMutableList()
            dataList.removeAt(index)
            submitList(dataList.toList())
        }
    }

    fun updateItem(item: RecyclerViewItem) {
        val index = getItemIndex(item)
        if (index != -1) {
            val dataList = currentList.toMutableList()
            dataList[index] = item
            submitList(dataList.toList())
        }
    }

    /**
     *
     */
    @Suppress("UNCHECKED_CAST")
    fun getItems(): List<ItemType> {
        val items = mutableListOf<ItemType>()
        this.currentList.map { items.add(it as ItemType) }
        return Collections.unmodifiableList(items)
    }

    /**
     *
     */
    @Suppress("UNCHECKED_CAST")
    fun getItemById(id: String): ItemType? {
        var item: ItemType? = null

        for (obj in currentList) {
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
        if (index < 0 || index > this.currentList.size - 1) {
            return null
        }
        return currentList[index] as ItemType
    }


    /**
     * If item is available in list, this will return index of that item.
     * Else it will return -1.
     */
    private fun getItemIndex(item: RecyclerViewItem): Int {
        var resultIndex = -1

        for ((index, obj) in currentList.withIndex()) {
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