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

package xyz.klinker.android.drag_dismiss;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * Scroll listener for interacting with the toolbar when the recyclerview scrolls. This includes
 * hiding the toolbar and showing it again when appropriate, along with changing the colors.
 */
final class ToolbarScrollListener extends RecyclerView.OnScrollListener {

    private static final int ANIMATION_DURATION = 200; // ms

    private Toolbar toolbar;
    private View statusBar;
    private int primaryColor;
    private int transparentColor;
    private boolean transparentBackground = true;
    private boolean isUpdatingTranslation = false;
    private boolean isUpdatingBackground = false;

    ToolbarScrollListener(Toolbar toolbar, View statusBar, int primaryColor) {
        this.toolbar = toolbar;
        this.statusBar = statusBar;
        this.primaryColor = primaryColor;
        this.transparentColor = toolbar.getContext().getResources()
                .getColor(R.color.dragdismiss_toolbarBackground);
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int firstItem = manager.findFirstCompletelyVisibleItemPosition();
        if (newState == RecyclerView.SCROLL_STATE_IDLE &&!transparentBackground &&
                firstItem == 0 && !isUpdatingBackground) {
            animateBackgroundColor(primaryColor, transparentColor, new DecelerateInterpolator());
            transparentBackground = true;
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int minDistance = toolbar.getContext().getResources()
                .getDimensionPixelSize(R.dimen.dragdismiss_minToolbarScroll);
        if (Math.abs(dy) < minDistance) {
            return;
        }

        if (dy > 0 && toolbar.getTranslationY() == 0) {
            Interpolator interpolator = new AccelerateInterpolator();

            if (!isUpdatingTranslation) {
                animateTranslation(-1 * toolbar.getHeight(), interpolator);
            }

            if (transparentBackground && !isUpdatingBackground) {
                animateBackgroundColor(transparentColor, primaryColor, interpolator);
                transparentBackground = false;
            }
        } else if (dy < 0 && toolbar.getTranslationY() != 0) {
            Interpolator interpolator = new DecelerateInterpolator();

            if (!isUpdatingTranslation) {
                animateTranslation(0, interpolator);
            }

            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int firstItem = manager.findFirstVisibleItemPosition();
            if (!transparentBackground && firstItem == 0 && !isUpdatingBackground) {
                animateBackgroundColor(primaryColor, transparentColor, interpolator);
                transparentBackground = true;
            }
        }
    }

    private void animateTranslation(int to, Interpolator interpolator) {
        toolbar.animate()
                .translationY(to)
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(interpolator)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        isUpdatingTranslation = false;
                    }
                })
                .start();
        isUpdatingTranslation = true;
    }

    private void animateBackgroundColor(int from, int to, Interpolator interpolator) {
        ValueAnimator anim = new ValueAnimator();
        anim.setIntValues(from, to);
        anim.setEvaluator(new ArgbEvaluator());
        anim.setInterpolator(interpolator);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                toolbar.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
                statusBar.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
            }
        });
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isUpdatingBackground = false;
            }
        });

        anim.setDuration(ANIMATION_DURATION);
        anim.start();
        isUpdatingBackground = true;
    }

}
