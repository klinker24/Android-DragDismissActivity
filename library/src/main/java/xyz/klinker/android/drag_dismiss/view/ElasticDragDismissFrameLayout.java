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

package xyz.klinker.android.drag_dismiss.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import xyz.klinker.android.drag_dismiss.R;

/**
 * A {@link FrameLayout} which responds to nested scrolls to create drag-dismissable layouts.
 * Applies an elasticity factor to reduce movement as you approach the given dismiss distance.
 * Optionally also scales down content during drag.
 * <p>
 * Adapted from
 * https://github.com/nickbutcher/plaid/blob/master/app/src/main/java/io/plaidapp/
 *      ui/widget/ElasticDragDismissFrameLayout.java
 * with some changes so that the background color can be adjusted behind the dragging view.
 * <p>
 * This view is required to only have a single child.
 */
public class ElasticDragDismissFrameLayout extends FrameLayout {

    // configurable attribs
    private float dragDismissDistance = Float.MAX_VALUE;
    private float dragDismissFraction = -1f;
    private float dragDismissScale = 1f;
    private boolean shouldScale = false;
    private float dragElasticity = 0.5f;

    // state
    private float totalDrag;
    private boolean draggingDown = false;
    private boolean draggingUp = false;

    private boolean enabled = true;

    private static Interpolator fastOutSlowInInterpolator;

    private List<ElasticDragDismissCallback> callbacks;

    private RectF draggingBackground;
    private Paint draggingBackgroundPaint;

    public ElasticDragDismissFrameLayout(Context context) {
        this(context, null, 0);
    }

    public ElasticDragDismissFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ElasticDragDismissFrameLayout(Context context, AttributeSet attrs,
                                         int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        dragDismissDistance = getResources()
                .getDimensionPixelSize(R.dimen.dragdismiss_dragDownDismissDistance);

        shouldScale = dragDismissScale != 1f;

        draggingBackgroundPaint = new Paint();
        draggingBackgroundPaint.setColor(getContext().getResources()
                .getColor(R.color.dragdismiss_transparentSideBackground));
        draggingBackgroundPaint.setStyle(Paint.Style.FILL);
    }

    public static abstract class ElasticDragDismissCallback {

        /**
         * Called for each drag event.
         *
         * @param elasticOffset       Indicating the drag offset with elasticity applied i.e. may
         *                            exceed 1.
         * @param elasticOffsetPixels The elastically scaled drag distance in pixels.
         * @param rawOffset           Value from [0, 1] indicating the raw drag offset i.e.
         *                            without elasticity applied. A value of 1 indicates that the
         *                            dismiss distance has been reached.
         * @param rawOffsetPixels     The raw distance the user has dragged
         */
        public void onDrag(float elasticOffset, float elasticOffsetPixels,
                           float rawOffset, float rawOffsetPixels) {
        }

        /**
         * Called when dragging is released and has exceeded the threshold dismiss distance.
         */
        public void onDragDismissed() {
        }

    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        if (enabled) {
            return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
        } else {
            return false;
        }
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (enabled) {
            // if we're in a drag gesture and the user reverses up the we should take those events
            if (draggingDown && dy > 0 || draggingUp && dy < 0) {
                dragScale(dy);
                consumed[1] = dy;
            }
        }
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed) {
        if (enabled) {
            dragScale(dyUnconsumed);
        }
    }

    @Override
    public void onStopNestedScroll(View child) {
        if (enabled) {
            if (Math.abs(totalDrag) >= dragDismissDistance) {
                dispatchDismissCallback();
            } else { // settle back to natural position
                if (fastOutSlowInInterpolator == null) {
                    fastOutSlowInInterpolator = AnimationUtils.loadInterpolator(getContext(),
                            android.R.interpolator.fast_out_slow_in);
                }
                getChildAt(0).animate()
                        .translationY(0f)
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(200L)
                        .setInterpolator(fastOutSlowInInterpolator)
                        .setListener(null)
                        .start();

                ValueAnimator animator = null;
                if (draggingUp) {
                    animator = ValueAnimator.ofFloat(draggingBackground.top,
                            draggingBackground.bottom);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            draggingBackground.top = (float) valueAnimator.getAnimatedValue();
                            invalidate();
                        }
                    });
                } else if (draggingDown) {
                    animator = ValueAnimator.ofFloat(draggingBackground.bottom,
                            draggingBackground.top);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            draggingBackground.bottom = (float) valueAnimator.getAnimatedValue();
                            invalidate();
                        }
                    });
                }

                if (animator != null) {
                    animator.setInterpolator(fastOutSlowInInterpolator);
                    animator.setDuration(200L);
                    animator.start();
                }

                totalDrag = 0;
                draggingDown = draggingUp = false;
                dispatchDragCallback(0f, 0f, 0f, 0f);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (dragDismissFraction > 0f) {
            dragDismissDistance = h * dragDismissFraction;
        }
    }

    public void addListener(ElasticDragDismissCallback listener) {
        if (callbacks == null) {
            callbacks = new ArrayList<>();
        }
        callbacks.add(listener);
    }

    public void setListener(ElasticDragDismissCallback listener) {
        callbacks = new ArrayList<>();
        callbacks.add(listener);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void removeListener(ElasticDragDismissCallback listener) {
        if (callbacks != null && callbacks.size() > 0) {
            callbacks.remove(listener);
        }
    }

    private void dragScale(int scroll) {
        if (scroll == 0) return;

        totalDrag += scroll;
        View child = getChildAt(0);

        // track the direction & set the pivot point for scaling
        // don't double track i.e. if play dragging down and then reverse, keep tracking as
        // dragging down until they reach the 'natural' position
        if (scroll < 0 && !draggingUp && !draggingDown) {
            draggingDown = true;
            if (shouldScale) child.setPivotY(getHeight());
        } else if (scroll > 0 && !draggingDown && !draggingUp) {
            draggingUp = true;
            if (shouldScale) child.setPivotY(0f);
        }
        // how far have we dragged relative to the distance to perform a dismiss
        // (0–1 where 1 = dismiss distance). Decreasing logarithmically as we approach the limit
        float dragFraction = (float) Math.log10(1 + (Math.abs(totalDrag) / dragDismissDistance));

        // calculate the desired translation given the drag fraction
        float dragTo = dragFraction * dragDismissDistance * dragElasticity;

        if (draggingUp) {
            // as we use the absolute magnitude when calculating the drag fraction, need to
            // re-apply the drag direction
            dragTo *= -1;
        }
        child.setTranslationY(dragTo);

        if (draggingBackground == null) {
            draggingBackground = new RectF();
            draggingBackground.left = 0;
            draggingBackground.right = getWidth();
        }

        if (shouldScale) {
            final float scale = 1 - ((1 - dragDismissScale) * dragFraction);
            child.setScaleX(scale);
            child.setScaleY(scale);
        }

        // if we've reversed direction and gone past the settle point then clear the flags to
        // allow the list to get the scroll events & reset any transforms
        if ((draggingDown && totalDrag >= 0)
                || (draggingUp && totalDrag <= 0)) {
            totalDrag = dragTo = dragFraction = 0;
            draggingDown = draggingUp = false;
            child.setTranslationY(0f);
            child.setScaleX(1f);
            child.setScaleY(1f);
        }

        // draw the background above or below the view where it has scrolled at
        if (draggingUp) {
            draggingBackground.bottom = getHeight();
            draggingBackground.top = getHeight() + dragTo;
            invalidate();
        } else if (draggingDown) {
            draggingBackground.top = 0;
            draggingBackground.bottom = dragTo;
            invalidate();
        }

        dispatchDragCallback(dragFraction, dragTo,
                Math.min(1f, Math.abs(totalDrag) / dragDismissDistance), totalDrag);
    }

    private void dispatchDragCallback(float elasticOffset, float elasticOffsetPixels,
                                      float rawOffset, float rawOffsetPixels) {
        if (callbacks != null && !callbacks.isEmpty()) {
            for (ElasticDragDismissCallback callback : callbacks) {
                callback.onDrag(elasticOffset, elasticOffsetPixels,
                        rawOffset, rawOffsetPixels);
            }
        }
    }

    private void dispatchDismissCallback() {
        if (callbacks != null && !callbacks.isEmpty()) {
            for (ElasticDragDismissCallback callback : callbacks) {
                callback.onDragDismissed();
            }
        }
    }

    public boolean isDragging() {
        return draggingDown || draggingUp;
    }

    @Override
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if (draggingBackground != null) {
            canvas.drawRect(draggingBackground, draggingBackgroundPaint);
        }
    }

}
