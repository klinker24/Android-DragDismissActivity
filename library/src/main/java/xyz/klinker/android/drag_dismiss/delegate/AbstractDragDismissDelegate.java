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

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import xyz.klinker.android.drag_dismiss.DragDismissIntentBuilder;
import xyz.klinker.android.drag_dismiss.R;
import xyz.klinker.android.drag_dismiss.util.ColorUtils;
import xyz.klinker.android.drag_dismiss.util.StatusBarHelper;
import xyz.klinker.android.drag_dismiss.view.ElasticDragDismissFrameLayout;

public abstract class AbstractDragDismissDelegate {
    
    abstract int getLayout();
    
    protected AppCompatActivity activity;
    
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private View statusBar;

    private String dragElasticity;
    private String theme;
    private boolean fullscreenForTablets;
    private String toolbarTitle;
    private int primaryColor;
    private boolean shouldShowToolbar;
    private boolean shouldScrollToolbar;
    
    AbstractDragDismissDelegate(AppCompatActivity activity) {
        this.activity = activity;
    }

    @CallSuper
    public void onCreate(Bundle savedInstanceState) {
        getIntentExtras();
        activity.setContentView(getLayout());

        progressBar = (ProgressBar) activity.findViewById(R.id.dragdismiss_loading);
        toolbar = (Toolbar) activity.findViewById(R.id.dragdismiss_toolbar);
        appBarLayout = (AppBarLayout) activity.findViewById(R.id.dragdismiss_app_bar);
        statusBar = activity.findViewById(R.id.dragdismiss_status_bar);

        setupToolbar();
        setupDragDismiss();
        changeColors();
    }

    /**
     * Show the {@link ProgressBar}.
     */
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the {@link ProgressBar}.
     */
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    private void getIntentExtras() {
        dragElasticity = activity.getIntent().getStringExtra(DragDismissIntentBuilder.EXTRA_DRAG_ELASTICITY);
        theme = activity.getIntent().getStringExtra(DragDismissIntentBuilder.EXTRA_THEME);
        if (DragDismissIntentBuilder.Theme.LIGHT.name().equals(theme)) {
            activity.getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if (DragDismissIntentBuilder.Theme.DARK.name().equals(theme)) {
            activity.getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else if (DragDismissIntentBuilder.Theme.BLACK.name().equals(theme)) {
            activity.getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            activity.getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
        }

        this.fullscreenForTablets = activity.getIntent().getBooleanExtra(DragDismissIntentBuilder.EXTRA_FULLSCREEN_FOR_TABLETS, false);
        this.shouldScrollToolbar = activity.getIntent().getBooleanExtra(DragDismissIntentBuilder.EXTRA_SHOULD_SCROLL_TOOLBAR, true);
        this.shouldShowToolbar = activity.getIntent().getBooleanExtra(DragDismissIntentBuilder.EXTRA_SHOULD_SHOW_TOOLBAR, true);
        this.toolbarTitle = activity.getIntent().getStringExtra(DragDismissIntentBuilder.EXTRA_TOOLBAR_TITLE);
        this.primaryColor = activity.getIntent().getIntExtra(DragDismissIntentBuilder.EXTRA_PRIMARY_COLOR,
                DragDismissIntentBuilder.DEFAULT_TOOLBAR_RESOURCE);
    }

    private void setupToolbar() {
        activity.setSupportActionBar(toolbar);
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.dragdismiss_ic_close);
            activity.getSupportActionBar().setTitle(toolbarTitle);
        }

        if (!shouldShowToolbar) {
            toolbar.setVisibility(View.GONE);
        }

        int statusBarHeight = StatusBarHelper.getStatusBarHeight(activity);
        statusBar.getLayoutParams().height = statusBarHeight;

        if (appBarLayout == null) {
            ((CoordinatorLayout.LayoutParams) toolbar.getLayoutParams()).topMargin = statusBarHeight;
        } else {
            ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).topMargin = statusBarHeight;
        }
    }

    private void setupDragDismiss() {
        ElasticDragDismissFrameLayout dragDismissLayout = (ElasticDragDismissFrameLayout)
                activity.findViewById(R.id.dragdismiss_drag_dismiss_layout);

        if (fullscreenForTablets) {
            activity.findViewById(R.id.dragdismiss_transparent_side_1).setVisibility(View.GONE);
            activity.findViewById(R.id.dragdismiss_transparent_side_2).setVisibility(View.GONE);

            dragDismissLayout.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            dragDismissLayout.invalidate();
        } else {
            View.OnClickListener sideClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.supportFinishAfterTransition();
                }
            };

            activity.findViewById(R.id.dragdismiss_transparent_side_1).setOnClickListener(sideClickListener);
            activity.findViewById(R.id.dragdismiss_transparent_side_2).setOnClickListener(sideClickListener);
        }

        dragDismissLayout.addListener(new ElasticDragDismissFrameLayout.ElasticDragDismissCallback() {
            @Override
            public void onDragDismissed() {
                super.onDragDismissed();
                activity.supportFinishAfterTransition();
            }
        });

        if (DragDismissIntentBuilder.DragElasticity.XXLARGE.name().equals(dragElasticity)) {
            dragDismissLayout.setDragElasticity(ElasticDragDismissFrameLayout.DRAG_ELASTICITY_XXLARGE);
            dragDismissLayout.halfDistanceRequired();
        } else if (DragDismissIntentBuilder.DragElasticity.XLARGE.name().equals(dragElasticity)) {
            dragDismissLayout.setDragElasticity(ElasticDragDismissFrameLayout.DRAG_ELASTICITY_XLARGE);
            dragDismissLayout.halfDistanceRequired();
        } else if (DragDismissIntentBuilder.DragElasticity.LARGE.name().equals(dragElasticity)) {
            dragDismissLayout.setDragElasticity(ElasticDragDismissFrameLayout.DRAG_ELASTICITY_LARGE);
        } else {
            dragDismissLayout.setDragElasticity(ElasticDragDismissFrameLayout.DRAG_ELASTICITY_NORMAL);
        }
    }

    private void changeColors() {
        ColorUtils.changeProgressBarColors(progressBar, primaryColor);
        if (DragDismissIntentBuilder.Theme.BLACK.name().equals(theme)) {
            activity.findViewById(R.id.dragdismiss_background_view).setBackgroundColor(Color.BLACK);
        }
    }

    /**
     * Get the {@link AppCompatActivity} that this delegate is set up on.
     *
     * @return {@link AppCompatActivity} for this delegate.
     */
    public AppCompatActivity getActivity() {
        return activity;
    }

    /**
     * Get the {@link ProgressBar} that was created by the delegate.
     *
     * @return {@link ProgressBar} created by the delegate.
     */
    public ProgressBar getProgressBar() {
        return progressBar;
    }

    /**
     * Get the {@link Toolbar} that was created by the delegate.
     *
     * @return {@link Toolbar} created by the delegate.
     */
    public Toolbar getToolbar() {
        return toolbar;
    }

    /**
     * Get the status bar {@link View} that was created by the delegate.
     *
     * @return status bar {@link View} created by the delegate.
     */
    public View getStatusBar() {
        return statusBar;
    }

    /**
     * Get the extra passed through the {@link DragDismissIntentBuilder}.
     *
     * @return the title for the {@link Activity}.
     */
    public String getToolbarTitle() {
        return toolbarTitle;
    }

    /**
     * Get the extra passed through the {@link DragDismissIntentBuilder}.
     *
     * @return the primary color for the {@link Activity}.
     */
    public int getPrimaryColor() {
        return primaryColor;
    }

    /**
     * Get the extra passed through the {@link DragDismissIntentBuilder}.
     *
     * @return whether or not the {@link ElasticDragDismissFrameLayout} should cover the full screen.
     */
    public boolean isFullscreenForTablets() {
        return fullscreenForTablets;
    }

    /**
     * Get the extra passed through the {@link DragDismissIntentBuilder}.
     *
     * @return whether or not the {@link Toolbar} should be displayed.
     */
    public boolean shouldShowToolbar() {
        return shouldShowToolbar;
    }

    /**
     * Get the extra passed through the {@link DragDismissIntentBuilder}.
     *
     * @return whether or not the {@link Toolbar} should scroll with the content.
     */
    public boolean shouldScrollToolbar() {
        return shouldScrollToolbar;
    }
}
