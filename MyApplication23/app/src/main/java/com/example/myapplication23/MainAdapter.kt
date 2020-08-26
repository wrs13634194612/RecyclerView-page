package com.example.myapplication23


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: MutableList<Message> = mutableListOf()
    private var isLoaderVisible = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_MESSAGE -> {
                val view = layoutInflater.inflate(R.layout.item_message, parent, false)
                ItemViewHolder(view)
            }
            else -> {
                val view = layoutInflater.inflate(R.layout.item_loading, parent, false)
                LoadingViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_MESSAGE -> {
                val item = holder as ItemViewHolder
                item.bind(items[position])
            }
            VIEW_TYPE_LOADING -> {
                val item = holder as LoadingViewHolder
                item.bind()
            }
        }
    }

    override fun getItemCount(): Int {
        return when(items) {
            null -> 0
            else -> items.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoaderVisible) {
            when (position) {
                items.size -1 -> VIEW_TYPE_LOADING
                else -> VIEW_TYPE_MESSAGE
            }
        } else {
            VIEW_TYPE_MESSAGE
        }
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.tv_title)
        private val description = itemView.findViewById<TextView>(R.id.tv_description)

        fun bind(item: Message) {
            title.text = item.title
            description.text = item.description
        }
    }

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val progressBar = itemView.findViewById<ProgressBar>(R.id.progress_bar)

        fun bind() {}
    }

    private fun add(message: Message) {
        items.add(message)
        notifyItemInserted(items.size -1)
    }

    fun addAll(messages: List<Message>) {
        for (items in messages) {
            add(items)
        }
    }

    private fun remove(message: Message) {
        val position = items.indexOf(message)

        if (position > -1) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun addLoading() {
        isLoaderVisible = true
        add(Message("张飞", "赵云"))
    }

    fun removeLoading() {
        isLoaderVisible = false
        val position = items.size - 1
        val item = getItem(position)

        if (item != null) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        while (itemCount > 0) {
            remove(getItem(0))
        }
    }

    private fun getItem(position: Int): Message {
        return items[position]
    }


    companion object {
        const val VIEW_TYPE_MESSAGE = 0
        const val VIEW_TYPE_LOADING = 1
    }
}