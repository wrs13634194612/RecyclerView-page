package com.example.myapplication24;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainAdapter adapter;
    private RecyclerView recycler_view;
    private int PAGE_START = 1;
    private int itemCount = 0;
    private int currentPage = PAGE_START;
    private int totalPage = 10;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private List<MessageBean> list = new ArrayList<>();
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler_view = findViewById(R.id.recycler_view);
        initListItem();  //预加载数据
        layoutManager = new
                LinearLayoutManager(this);
        recycler_view.setLayoutManager(layoutManager);
        adapter = new MainAdapter();
        recycler_view.addOnScrollListener(new EndlessScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                initListItem();
            }

            @Override
            protected Boolean isLastPage() {
                return isLastPage;
            }

            @Override
            protected Boolean isLoading() {
                return isLoading;
            }
        });
        recycler_view.setAdapter(adapter);

    }

    private void initListItem() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list.clear();  //把之前的清空掉，只加载新加载的，完美
                for (int i = 0; i < 10; i++) {
                    itemCount++;
                    list.add(new MessageBean("title" + itemCount, "Description" + itemCount));
                }
                if (currentPage != PAGE_START) {
                    adapter.removeLoading();
                }
                adapter.addAll(list);
                if (currentPage < totalPage) {
                    adapter.addLoading();
                } else {
                    isLastPage = true;
                }
                isLoading = false;
            }
        }, 1500);

    }


}