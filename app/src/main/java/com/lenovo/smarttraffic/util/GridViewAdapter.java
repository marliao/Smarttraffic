package com.lenovo.smarttraffic.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {

    private Context context;
    private List<String> categorylist;
    private int hideProsition = AdapterView.INVALID_POSITION;

    public GridViewAdapter(Context context, List<String> categorylist) {
        this.context = context;
        this.categorylist = categorylist;
    }

    @Override
    public int getCount() {
        return categorylist.size();
    }

    @Override
    public String getItem(int position) {
        return categorylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO: 2019/6/2
        if (hideProsition == position) {
            convertView.setVisibility(View.INVISIBLE);
        } else {
            convertView.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    public void hideView(int pos) {
        hideProsition = pos;
        notifyDataSetInvalidated();
    }

    public void showHideView() {
        hideProsition = AdapterView.INVALID_POSITION;
        notifyDataSetChanged();
    }

    public void swapView(int draggedPos, int destPos) {
        if (draggedPos < destPos) {
            categorylist.add(destPos + 1, getItem(draggedPos));
            categorylist.remove(draggedPos);
        }
        if (draggedPos > destPos) {
            categorylist.add(destPos, getItem(draggedPos));
            categorylist.remove(draggedPos + 1);
        }
        hideProsition = destPos;
        notifyDataSetChanged();
    }

}
