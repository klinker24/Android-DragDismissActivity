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

package xyz.klinker.android.drag_dismiss.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import xyz.klinker.android.drag_dismiss.R;
import xyz.klinker.android.drag_dismiss.util.ColorUtils;
import xyz.klinker.android.drag_dismiss.view.ToolbarScrollListener;

/**
 * Activity that allows you to drag and dismiss a RecyclerView, whenever you reach the top or
 * the bottom of the list.
 * <p/>
 * You will have to set up the RecyclerView in the abstract setupRecyclerView method. Within that
 * method, you should set the adapter and the LayoutManager.
 */
public abstract class DragDismissRecyclerViewActivity extends AbstractDragDismissActivity {

    protected abstract void setupRecyclerView(RecyclerView recyclerView);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.dragdismiss_recycler);

        if (shouldScrollToolbar && shouldShowToolbar) {
            recyclerView.addOnScrollListener(new ToolbarScrollListener(toolbar, statusBar, primaryColor));
        } else {
            toolbar.setBackgroundColor(primaryColor);
            statusBar.setBackgroundColor(primaryColor);
        }

        ColorUtils.changeRecyclerOverscrollColors(recyclerView, primaryColor);
        setupRecyclerView(recyclerView);
    }

    @Override
    protected final int getLayout() {
        return R.layout.dragdismiss_activity_recycler;
    }
}
