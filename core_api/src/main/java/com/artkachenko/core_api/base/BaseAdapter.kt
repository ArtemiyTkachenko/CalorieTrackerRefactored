package com.artkachenko.core_api.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.artkachenko.core_api.utils.debugLog

abstract class BaseAdapter <T> (private val actions: ViewHolderActions<T> ?= null) : RecyclerView.Adapter<BaseViewHolder<T>>() {

    private val items = mutableListOf<T>()

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        debugLog("onBindViewHolder called")
        holder.bind(model = items[position])
    }

    override fun getItemCount() = items.size

    fun setInitial(data: List<T>, callback: (() -> Unit)? = null) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
        callback?.invoke()
    }

    fun setData(data: List<T>, callback: (() -> Unit)? = null) {
        val start = items.size
        items.addAll(data)
        notifyItemRangeChanged(start, start + data.size)
        callback?.invoke()
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }
}

abstract class BaseViewHolder<T>(itemView: View, private val actions: ViewHolderActions<T> ?= null) : RecyclerView.ViewHolder(itemView) {

    protected var model: T? = null

    fun bindHolder(item: Any) {
        (item as T).apply {
            model = this
            bind(this)
        }
    }

    abstract fun bind(model: T)
}

interface ViewHolderActions<T> {

    fun onItemClicked(model: T, view: View) {}
}