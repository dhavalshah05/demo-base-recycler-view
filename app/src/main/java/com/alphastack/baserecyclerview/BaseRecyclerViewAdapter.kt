package com.alphastack.baserecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alphastack.baserecyclerview.model.BugTrackerObject
import com.alphastack.baserecyclerview.model.DataNotFoundItem
import com.alphastack.baserecyclerview.model.LoadingItem
import java.util.*

abstract class BaseRecyclerViewAdapter<ItemType : BugTrackerObject, VH : BaseViewHolder<ItemType>> :
        RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface LoadMoreDataListener {
        fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?)
    }

    companion object {
        private const val ITEM_TYPE_DATA_NOT_FOUND = 111
        private const val ITEM_TYPE_DATA = 222
        private const val ITEM_TYPE_LOADING = 333
    }

    /**
     * Holds items.
     */
    private val items = mutableListOf<BugTrackerObject>()

    /**
     * Holds scroll listener.
     */
    private var endlessRecyclerViewScrollListener: EndlessRecyclerViewScrollListener? = null

    /**
     * Holds reference of RecyclerView to attach ScrollListener
     */
    private var recyclerView: RecyclerView? = null

    /**
     *
     */
    private var loadMoreDataListener: LoadMoreDataListener? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView

        endlessRecyclerViewScrollListener = object : EndlessRecyclerViewScrollListener(recyclerView.layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                view?.post {
                    this@BaseRecyclerViewAdapter.loadMoreDataListener?.onLoadMore(page, totalItemsCount, view)
                }
            }
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
        endlessRecyclerViewScrollListener = null
    }

    /**
     *
     */
    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is DataNotFoundItem -> ITEM_TYPE_DATA_NOT_FOUND
            is LoadingItem -> ITEM_TYPE_LOADING
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
        } else if (itemType == ITEM_TYPE_LOADING && holder is LoadingItemViewHolder) {
            val item = items[position] as LoadingItem
            holder.bind(item)
        }

    }

    fun setLoadMoreDataListener(listener: LoadMoreDataListener) {
        this.loadMoreDataListener = listener
    }


    /**
     *
     */
    @Suppress("MemberVisibilityCanBePrivate")
    fun showDataNotFoundMessage(message: String) {
        clearData()

        val item = DataNotFoundItem(message)
        this.items.add(item)
        notifyDataSetChanged()

        removeEndlessScrollListener()
    }

    /**
     *
     */
    fun showLoading() {
        val loadingItem = LoadingItem()
        loadingItem.id = -1L

        if (items.isEmpty()) {
            clearData()
            this.items.add(loadingItem)
            notifyDataSetChanged()
        } else {
            if (itemCount == 1 && items[0] is DataNotFoundItem) {
                clearData()
                this.items.add(loadingItem)
                notifyDataSetChanged()
            } else {
                val indexToInsert = itemCount
                this.items.add(indexToInsert, loadingItem)
                this.notifyItemInserted(indexToInsert)
            }
        }

        removeEndlessScrollListener()
    }

    /**
     *
     */
    fun hideLoading() {
        removeLoadingItem()
        addEndlessScrollListener()
    }

    /**
     *
     */
    fun replaceData(items: List<BugTrackerObject>) {
        clearData()

        this.items.addAll(items)
        notifyDataSetChanged()

        addEndlessScrollListener()
    }

    /**
     *
     */
    fun appendData(items: List<BugTrackerObject>) {
        removeLoadingItem()
        removeDataNotFoundItem()

        val startIndex = itemCount
        this.items.addAll(startIndex, items)
        notifyItemRangeInserted(startIndex, items.size)

        addEndlessScrollListener()
    }


    /**
     *
     */
    fun appendData(index: Int, items: List<BugTrackerObject>) {
        removeLoadingItem()
        removeDataNotFoundItem()

        this.items.addAll(index, items)
        notifyItemRangeInserted(index, items.size)

        addEndlessScrollListener()
    }


    /**
     *
     */
    @Suppress("MemberVisibilityCanBePrivate")
    fun clearData() {
        this.items.clear()
        this.endlessRecyclerViewScrollListener?.resetState()
        notifyDataSetChanged()
    }

    /**
     *
     */
    fun addItem(item: BugTrackerObject) {
        removeLoadingItem()
        removeDataNotFoundItem()

        // Add item
        val indexToInsert = itemCount
        this.items.add(indexToInsert, item)
        this.notifyItemInserted(indexToInsert)

        addEndlessScrollListener()
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
            if (item !is DataNotFoundItem && item !is LoadingItem) {
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
     *
     */
    private fun addEndlessScrollListener() {
        if (recyclerView != null && endlessRecyclerViewScrollListener != null) {
            recyclerView!!.removeOnScrollListener(endlessRecyclerViewScrollListener!!)
            recyclerView!!.addOnScrollListener(endlessRecyclerViewScrollListener!!)
        }
    }

    /**
     *
     */
    private fun removeEndlessScrollListener() {
        if (recyclerView != null && endlessRecyclerViewScrollListener != null) {
            recyclerView!!.removeOnScrollListener(endlessRecyclerViewScrollListener!!)
        }
    }

    /**
     *
     */
    private fun removeLoadingItem() {
        if (this.items.isNotEmpty() && this.items.last() is LoadingItem) {
            val itemIndex = itemCount - 1
            this.items.removeAt(itemIndex)
            notifyItemRemoved(itemIndex)
        }
    }

    /**
     *
     */
    private fun removeDataNotFoundItem() {
        if (this.items.isNotEmpty() && this.items.last() is DataNotFoundItem) {
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