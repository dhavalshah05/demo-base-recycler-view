package com.alphastack.superadapter.viewholder

import android.content.Context
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<ItemType>(view: View) : RecyclerView.ViewHolder(view) {

    /**
     *
     */
    protected fun getContext(): Context = itemView.context

    /**
     *
     */
    protected fun getString(@StringRes resId: Int) = getContext().getString(resId)

    /**
     *
     */
    protected fun getColor(@ColorRes resId: Int) = ContextCompat.getColor(getContext(), resId)

    /**
     *
     */
    protected fun getDrawable(@DrawableRes resId: Int) = ContextCompat.getDrawable(getContext(), resId)

    /**
     *
     */
    abstract fun bind(item: ItemType)
}