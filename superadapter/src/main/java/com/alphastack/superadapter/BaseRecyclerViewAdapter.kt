package com.alphastack.superadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alphastack.superadapter.model.RecyclerViewDataNotFoundItem
import com.alphastack.superadapter.model.RecyclerViewItem
import com.alphastack.superadapter.model.RecyclerViewLoadingItem
import com.alphastack.superadapter.viewholder.BaseViewHolder
import com.alphastack.superadapter.viewholder.DataNotFoundViewHolder
import com.alphastack.superadapter.viewholder.LoadingItemViewHolder
import java.util.*

abstract class BaseRecyclerViewAdapter<ItemType : RecyclerViewItem, VH : BaseViewHolder<ItemType>> :
        RecyclerView.Adapter<BaseViewHolder<*>>() {


    companion object {
        private const val ITEM_TYPE_DATA_NOT_FOUND = 111
        private const val ITEM_TYPE_DATA = 222
        private const val ITEM_TYPE_LOADING = 333
    }

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
    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is RecyclerViewDataNotFoundItem -> ITEM_TYPE_DATA_NOT_FOUND
            is RecyclerViewLoadingItem -> ITEM_TYPE_LOADING
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
        } else if (viewType == ITEM_TYPE_LOADING) {

            val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_loading_item, parent, false)
            viewHolder = LoadingItemViewHolder(view)

        } else {
            viewHolder = getViewHolder(LayoutInflater.from(parent.context), parent, viewType)
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
            val item = items[position] as RecyclerViewDataNotFoundItem
            holder.bind(item)
        } else if (itemType == ITEM_TYPE_LOADING && holder is LoadingItemViewHolder) {

            // Set LoadingItem size
            val layoutParams = holder.itemView.layoutParams as RecyclerView.LayoutParams
            if (recyclerViewHeight != 0 && recyclerViewWidth != 0 && getItems().isEmpty()) {
                layoutParams.width = recyclerViewWidth
                layoutParams.height = recyclerViewHeight
            } else {
                layoutParams.width = RecyclerView.LayoutParams.MATCH_PARENT
                layoutParams.height = holder.itemView.context.resources.getDimension(R.dimen.recycler_view_loading_item_height).toInt()
            }
            holder.itemView.layoutParams = layoutParams

            val item = items[position] as RecyclerViewLoadingItem
            holder.bind(item)
        }

    }

    /**
     *
     */
    @Suppress("MemberVisibilityCanBePrivate")
    fun showDataNotFoundMessage(message: String) {
        clearData()

        val item = RecyclerViewDataNotFoundItem(message)
        this.items.add(item)
        notifyDataSetChanged()
    }

    /**
     *
     */
    fun showLoading() {
        val loadingItem = RecyclerViewLoadingItem()

        if (items.isEmpty()) {
            clearData()
            this.items.add(loadingItem)
            notifyDataSetChanged()
        } else {
            if (itemCount == 1 && items[0] is RecyclerViewDataNotFoundItem) {
                clearData()
                this.items.add(loadingItem)
                notifyDataSetChanged()
            } else {
                val indexToInsert = itemCount
                this.items.add(indexToInsert, loadingItem)
                this.notifyItemInserted(indexToInsert)
            }
        }
    }

    /**
     *
     */
    fun hideLoading() {
        removeLoadingItem()
    }

    /**
     *
     */
    fun replaceData(items: List<RecyclerViewItem>) {
        clearData()

        this.items.addAll(items)
        notifyDataSetChanged()
    }

    /**
     *
     */
    fun appendData(items: List<RecyclerViewItem>) {
        removeLoadingItem()
        removeDataNotFoundItem()

        val startIndex = itemCount
        this.items.addAll(startIndex, items)
        notifyItemRangeInserted(startIndex, items.size)
    }


    /**
     *
     */
    fun appendData(index: Int, items: List<RecyclerViewItem>) {
        removeLoadingItem()
        removeDataNotFoundItem()

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
    fun addItem(item: RecyclerViewItem) {
        removeLoadingItem()
        removeDataNotFoundItem()

        // Add item
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
            checkForDataNotFoundState()
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
        for (item in this.items) {
            if (item !is RecyclerViewDataNotFoundItem && item !is RecyclerViewLoadingItem) {
                items.add(item as ItemType)
            }
        }
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
    @Suppress("MemberVisibilityCanBePrivate")
    fun getItem(index: Int): RecyclerViewItem? {
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
     *
     */
    private fun removeLoadingItem() {
        if (this.items.isNotEmpty() && this.items.last() is RecyclerViewLoadingItem) {
            val itemIndex = itemCount - 1
            this.items.removeAt(itemIndex)
            notifyItemRemoved(itemIndex)
        }
    }

    /**
     *
     */
    private fun removeDataNotFoundItem() {
        if (this.items.isNotEmpty() && this.items.last() is RecyclerViewDataNotFoundItem) {
            val itemIndex = itemCount - 1
            this.items.removeAt(itemIndex)
            notifyItemRemoved(itemIndex)
        }
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