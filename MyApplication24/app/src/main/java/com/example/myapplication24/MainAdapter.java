package com.example.myapplication24;

import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MessageBean> items = new ArrayList<>();
    private boolean isLoaderVisible = false;
    private static int VIEW_TYPE_MESSAGE = 0;
    private static int VIEW_TYPE_LOADING = 1;

    @Override
    public int getItemCount() {
        Log.e("TAG", "count:" + items.size());
        int count;
        if (items == null) {
            count = 0;
        } else {
            count = items.size();
        }
        return count;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_MESSAGE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
            return new MyViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_MESSAGE) {
            ((MyViewHolder) holder).description.setText(items.get(position).getDescription());
            ((MyViewHolder) holder).title.setText(items.get(position).getTitle());
        } else {
            //loading点击事件
        }
    }

    @Override
    public int getItemViewType(int position) {
        int result;
        if (isLoaderVisible) {
            if (position == items.size() - 1) {
                result = VIEW_TYPE_LOADING;
            } else {
                result = VIEW_TYPE_MESSAGE;
            }
        } else {
            result = VIEW_TYPE_MESSAGE;
        }
        return result;
    }


    private void add(MessageBean messageBean) {
        items.add(messageBean);
        notifyItemInserted(items.size() - 1);
    }

    public void addAll(List<MessageBean> mlist) {
        //  this.items.addAll(mlist); for循环遍历，遍历list元素内容
        for (MessageBean amessages : mlist) {
            add(amessages);
        }
    }

    public void remove(MessageBean messageBean) {
        int position = items.indexOf(messageBean);
        if (position > -1) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addLoading() {
        isLoaderVisible = true;
        add(new MessageBean("张飞", "赵云"));
    }

    public void removeLoading() {
        isLoaderVisible = false;
        int position = items.size() - 1;
        MessageBean item = getItem(position);

        if (item != null) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    private MessageBean getItem(int position) {
        return items.get(position);
    }

}
