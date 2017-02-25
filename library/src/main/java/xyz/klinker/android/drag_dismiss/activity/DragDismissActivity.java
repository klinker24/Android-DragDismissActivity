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
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import xyz.klinker.android.drag_dismiss.R;
import xyz.klinker.android.drag_dismiss.view.ToolbarScrollListener;

/**
 * An Activity that provides a Toolbar and drag-dismiss functionality for free. This Activity allows
 * you to add any content you want. Your content will be housed in a NestedScrollView, so you shouldn't
 * worry about the height of the content.
 */
public abstract class DragDismissActivity extends AbstractDragDismissActivity {

    protected abstract View onCreateContent(LayoutInflater inflater, ViewGroup parent);

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (shouldScrollToolbar && shouldShowToolbar) {
            NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.dragdismiss_scroll_view);
            scrollView.setOnScrollChangeListener(new ToolbarScrollListener(toolbar, statusBar, primaryColor));
        } else {
            toolbar.setBackgroundColor(primaryColor);
            statusBar.setBackgroundColor(primaryColor);
        }

        FrameLayout elasticContent = (FrameLayout) findViewById(R.id.dragdismiss_content);
        elasticContent.addView(onCreateContent(getLayoutInflater(), elasticContent));
    }

    @Override
    protected final int getLayout() {
        return R.layout.dragdismiss_activity;
    }
}
