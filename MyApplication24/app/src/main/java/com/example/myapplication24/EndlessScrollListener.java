package com.example.myapplication24;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager mlayoutManager;
    private Context context;
    private int PAGE_SIZE = 10;

    protected abstract void loadMoreItems();

    protected abstract Boolean isLastPage();

    protected abstract Boolean isLoading();


    public EndlessScrollListener(LinearLayoutManager mlayoutManager) {
        this.mlayoutManager = mlayoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = mlayoutManager.getChildCount();
        int totalItemCount = mlayoutManager.getItemCount();
        int firstVisibleItemPosition = mlayoutManager.findFirstVisibleItemPosition();

        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= PAGE_SIZE) {
                loadMoreItems();
            }
        }
    }
}
