/*
 * Copyright (C) 2017 Luke Klinker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.klinker.drag_dismiss.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import xyz.klinker.drag_dismiss.R;
import xyz.klinker.drag_dismiss.adapter.SampleViewHolder;

public class SampleAdapter extends RecyclerView.Adapter<SampleViewHolder> {

    private static final int TYPE_HEADER = 1;
    private static final int TYPE_CONTENT = 2;

    private String[] data;

    public SampleAdapter() {
        data = new String[50];
        for (int i = 0; i < data.length; i++) {
            data[i] = "Item " + (i + 1);
        }
    }

    @Override
    public SampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CONTENT) {
            return new SampleViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_item, parent, false));
        } else {
            return new SampleViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_header, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_CONTENT;
    }

    @Override
    public void onBindViewHolder(SampleViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_CONTENT) {
            holder.textView.setText(data[position - 1]);
        }
    }

    @Override
    public int getItemCount() {
        return data.length + 1;
    }

}
