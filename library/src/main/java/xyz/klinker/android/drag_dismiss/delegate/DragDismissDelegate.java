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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;
import xyz.klinker.android.drag_dismiss.R;
import xyz.klinker.android.drag_dismiss.util.AndroidVersionUtils;
import xyz.klinker.android.drag_dismiss.util.StatusBarHelper;
import xyz.klinker.android.drag_dismiss.view.ElasticDragDismissFrameLayout;
import xyz.klinker.android.drag_dismiss.view.ToolbarScrollListener;
import xyz.klinker.android.drag_dismiss.view.TransparentStatusBarInsetLayout;

public class DragDismissDelegate extends AbstractDragDismissDelegate {

    public interface Callback {
        View onCreateContent(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState);
    }

    private Callback callback;

    public DragDismissDelegate(AppCompatActivity activity, Callback callback) {
        super(activity);
        this.callback = callback;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (shouldScrollToolbar() && shouldShowToolbar()) {
            final ToolbarScrollListener scrollListener = new ToolbarScrollListener(getToolbar(), getStatusBar(), getPrimaryColor());
            final NestedScrollView scrollView = (NestedScrollView) activity.findViewById(R.id.dragdismiss_scroll_view);
            scrollView.setOnScrollChangeListener(scrollListener);

            ElasticDragDismissFrameLayout dragDismissLayout = (ElasticDragDismissFrameLayout)
                    activity.findViewById(R.id.dragdismiss_drag_dismiss_layout);
            dragDismissLayout.addListener(new ElasticDragDismissFrameLayout.ElasticDragDismissCallback() {
                @Override
                public void onDrag(float elasticOffset, float elasticOffsetPixels, float rawOffset, float rawOffsetPixels) {
                    if (elasticOffsetPixels > 10) {
                        scrollListener.onScrollChange(scrollView, 0, 0, 0, 1000);
                    }
                }
            });
            if (AndroidVersionUtils.isAndroidQ()) {
                transparentStatusBarLayout.setOnApplyInsetsListener(new TransparentStatusBarInsetLayout.AppliedInsets() {
                    @Override @SuppressLint("NewApi")
                    public void onApplyInsets(WindowInsets insets) {
                        scrollView.setPadding(0, 0, 0, insets.getSystemWindowInsetBottom());
                    }
                });
            }
        } else {
            getToolbar().setBackgroundColor(getPrimaryColor());
            getStatusBar().setBackgroundColor(getPrimaryColor());
        }

        FrameLayout elasticContent = (FrameLayout) activity.findViewById(R.id.dragdismiss_content);
        elasticContent.addView(callback.onCreateContent(activity.getLayoutInflater(), elasticContent, savedInstanceState));

        if (!drawUnderStatusBar) {
            ((NestedScrollView.LayoutParams) elasticContent.getLayoutParams()).topMargin = StatusBarHelper.getStatusBarHeight(activity);
        }
    }

    @Override
    int getLayout() {
        return R.layout.dragdismiss_activity;
    }
}
