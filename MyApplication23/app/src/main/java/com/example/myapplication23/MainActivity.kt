package com.example.myapplication23



import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MainAdapter

    private val PAGE_START = 1
    private var itemCount = 0
    private var currentPage = PAGE_START
    private var totalPage = 10
    private var isLastPage = false
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initListItem()
        initAdapter()
    }

    private fun initAdapter() {
        adapter = MainAdapter()

        recycler_view.addOnScrollListener(object : EndlessScrollListener(
            recycler_view.layoutManager as LinearLayoutManager
        ) {
            override fun loadMoreItems() {
                isLoading = true
                currentPage++
                initListItem()
            }

            override fun isLastPage() = isLastPage

            override fun isLoading() = isLoading
        })


        recycler_view.adapter = adapter
    }

    private fun initListItem() {
        val itemList: MutableList<Message> = mutableListOf()
        Handler().postDelayed({
            for (i in 0..9) {
                itemCount++
                val message = Message("App Title: $itemCount", "Description: $itemCount")
                itemList.add(message)
            }

            if (currentPage != PAGE_START) {
                adapter.removeLoading()
            }

            adapter.addAll(itemList)

            if (currentPage < totalPage) {
                adapter.addLoading()
            } else {
                isLastPage = true
            }

            isLoading = false

        }, 1500)
    }

}
