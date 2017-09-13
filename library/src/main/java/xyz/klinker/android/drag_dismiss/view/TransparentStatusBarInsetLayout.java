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

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.WindowInsets;
import android.widget.LinearLayout;

public class TransparentStatusBarInsetLayout extends LinearLayout {
    private int[] mInsets = new int[4];

    public TransparentStatusBarInsetLayout(Context context) {
        super(context);
    }

    public TransparentStatusBarInsetLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TransparentStatusBarInsetLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public final WindowInsets onApplyWindowInsets(WindowInsets insets) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            mInsets[0] = insets.getSystemWindowInsetLeft();
            mInsets[1] = insets.getSystemWindowInsetTop();
            mInsets[2] = insets.getSystemWindowInsetRight();
            return super.onApplyWindowInsets(insets.replaceSystemWindowInsets(0, 0, 0,
                    insets.getSystemWindowInsetBottom()));
        } else {
            return insets;
        }
    }
}
