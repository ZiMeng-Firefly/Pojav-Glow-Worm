package com.kdt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * Basic adapter, expect it uses the what is passed by the code, no the resources
 *
 * @param <T>
 */
public class SimpleArrayAdapter<T> extends BaseAdapter {
    private List<T> mObjects;

    public SimpleArrayAdapter(List<T> objects) {
        setObjects(objects);
    }

    public void setObjects(@Nullable List<T> objects) {
        if (objects == null) {
            if (mObjects != Collections.emptyList()) {
                mObjects = Collections.emptyList();
                notifyDataSetChanged();
            }
        } else {
            if (objects != mObjects) {
                mObjects = objects;
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public int getCount() {
        return mObjects.size();
    }

    @Override
    public T getItem(int position) {
        return mObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView v = (TextView) convertView;
        v.setText(mObjects.get(position).toString());
        return v;
    }
}
