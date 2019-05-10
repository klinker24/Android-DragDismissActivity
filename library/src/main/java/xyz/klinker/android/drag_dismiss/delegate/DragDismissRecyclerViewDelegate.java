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

package xyz.klinker.android.drag_dismiss.delegate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.WindowInsets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import xyz.klinker.android.drag_dismiss.R;
import xyz.klinker.android.drag_dismiss.util.AndroidVersionUtils;
import xyz.klinker.android.drag_dismiss.util.ColorUtils;
import xyz.klinker.android.drag_dismiss.view.ToolbarScrollListener;
import xyz.klinker.android.drag_dismiss.view.TransparentStatusBarInsetLayout;

public class DragDismissRecyclerViewDelegate extends AbstractDragDismissDelegate {

    public interface Callback {
        void setupRecyclerView(RecyclerView recyclerView);
    }

    private Callback callback;

    public DragDismissRecyclerViewDelegate(AppCompatActivity activity, Callback callback) {
        super(activity);
        this.callback = callback;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.dragdismiss_recycler);

        if (shouldScrollToolbar() && shouldShowToolbar()) {
            recyclerView.addOnScrollListener(new ToolbarScrollListener(getToolbar(), getStatusBar(), getPrimaryColor()));
        } else {
            getToolbar().setBackgroundColor(getPrimaryColor());
            getStatusBar().setBackgroundColor(getPrimaryColor());
        }

        if (AndroidVersionUtils.isAndroidQ()) {
            transparentStatusBarLayout.setOnApplyInsetsListener(new TransparentStatusBarInsetLayout.AppliedInsets() {
                @Override @SuppressLint("NewApi")
                public void onApplyInsets(WindowInsets insets) {
                    recyclerView.setPadding(0, 0, 0, insets.getSystemWindowInsetBottom());
                }
            });
        }

        ColorUtils.changeRecyclerOverscrollColors(recyclerView, getPrimaryColor());
        callback.setupRecyclerView(recyclerView);
    }

    @Override
    int getLayout() {
        return R.layout.dragdismiss_activity_recycler;
    }
}
