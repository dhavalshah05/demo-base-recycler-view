package com.alphastack.baserecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alphastack.baserecyclerview.model.BugTrackerObject
import com.alphastack.baserecyclerview.model.DataNotFoundItem
import java.util.*

abstract class BaseRecyclerViewAdapter<ItemType : BugTrackerObject, VH : BaseViewHolder<ItemType>> :
        RecyclerView.Adapter<BaseViewHolder<*>>() {

    companion object {
        private const val ITEM_TYPE_DATA_NOT_FOUND = 111
        private const val ITEM_TYPE_DATA = 222
    }

    /**
     * Holds items.
     */
    private val items = mutableListOf<BugTrackerObject>()

    /**
     *
     */
    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is DataNotFoundItem -> ITEM_TYPE_DATA_NOT_FOUND
            else -> getViewType(position)
        }
    }

    /**
     * Override this method if child Adapter has multiple
     * view types.
     */
    open fun getViewType(position: Int): Int {
        return ITEM_TYPE_DATA
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

            // Set Height & Width to MATCH_PARENT
            parent.post {
                val layoutParams = view.layoutParams as RecyclerView.LayoutParams
                layoutParams.width = RecyclerView.LayoutParams.MATCH_PARENT
                layoutParams.height = RecyclerView.LayoutParams.MATCH_PARENT
                view.layoutParams = layoutParams
            }

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

        if (itemType == getViewType(position)) {
            val item = items[position] as ItemType
            val viewHolder = holder as VH
            aboutToBindViewHolder(viewHolder, position)
            viewHolder.bind(item)
        } else if (itemType == ITEM_TYPE_DATA_NOT_FOUND && holder is DataNotFoundViewHolder) {
            val item = items[position] as DataNotFoundItem
            holder.bind(item)
        }

    }


    /**
     *
     */
    @Suppress("MemberVisibilityCanBePrivate")
    fun showDataNotFoundMessage(message: String) {
        val item = DataNotFoundItem(message)
        this.items.clear()
        this.items.add(item)
        notifyDataSetChanged()
    }

    /**
     *
     */
    fun replaceData(items: List<BugTrackerObject>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    /**
     *
     */
    fun appendData(items: List<BugTrackerObject>) {
        val startIndex = itemCount
        this.items.addAll(startIndex, items)
        notifyItemRangeInserted(startIndex, items.size)
    }

    /**
     *
     */
    fun appendData(index: Int, items: List<BugTrackerObject>) {
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
    fun addItem(item: BugTrackerObject) {
        // Remove DataNotFoundItem if previous data was empty
        if (this.items.size == 1 && getItem(0) is DataNotFoundItem) {
            clearData()
        }
        // Add item
        val indexToInsert = itemCount
        this.items.add(indexToInsert, item)
        this.notifyItemInserted(indexToInsert)
    }

    /**
     *
     */
    fun removeItem(item: BugTrackerObject) {
        val index = getItemIndex(item)
        if (index != -1) {
            this.items.removeAt(index)
            notifyItemRemoved(index)
            checkForDataNotFoundState()
        }
    }

    /**
     *
     */
    fun updateItem(item: BugTrackerObject) {
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
        for (item in this.items) {
            if (item !is DataNotFoundItem) {
                items.add(item as ItemType)
            }
        }
        return Collections.unmodifiableList(items)
    }

    /**
     *
     */
    @Suppress("UNCHECKED_CAST")
    fun getItemById(id: Long): ItemType? {
        var item: ItemType? = null

        for (obj in items) {
            if (obj.id == id) {
                item = obj as ItemType
                break
            }
        }

        return item
    }

    /**
     * This will return item from index or return null.
     */
    @Suppress("MemberVisibilityCanBePrivate")
    fun getItem(index: Int): BugTrackerObject? {
        // Return null if index is out of bound
        if (index < 0 || index > this.items.size - 1) {
            return null
        }

        /*
        * We cannot return ItemType because if item is type of DataNotFoundItem,
        * then it will throw an exception.
        * */
        return this.items[index]
    }

    /**
     * This will check if there are more items in list or not.
     * If not, it will show data not found state.
     */
    private fun checkForDataNotFoundState() {
        if (this.items.isEmpty()) {
            showDataNotFoundMessage("Data not found.")
        }
    }

    /**
     * If item is available in list, this will return index of that item.
     * Else it will return -1.
     */
    private fun getItemIndex(item: BugTrackerObject): Int {
        var resultIndex = -1

        for ((index, obj) in items.withIndex()) {
            if (obj.id == item.id) {
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
    abstract fun getViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH

}