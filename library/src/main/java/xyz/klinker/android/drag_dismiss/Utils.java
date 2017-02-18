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

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.widget.EdgeEffect;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Utilities we use, mostly for UI modification.
 */
final class Utils {

    /**
     * Changes the overscroll highlight effect on a recyclerview to be the given color.
     */
    static void changeRecyclerOverscrollColors(RecyclerView recyclerView, final int color) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean invoked = false;

            @Override
            @TargetApi(21)
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                // only invoke this once
                if (invoked) {
                    return;
                } else {
                    invoked = true;
                }

                try {
                    final Class<?> clazz = RecyclerView.class;

                    for (final String name : new String[]{"ensureTopGlow", "ensureBottomGlow"}) {
                        Method method = clazz.getDeclaredMethod(name);
                        method.setAccessible(true);
                        method.invoke(recyclerView);
                    }

                    for (final String name : new String[]{"mTopGlow", "mBottomGlow"}) {
                        final Field field = clazz.getDeclaredField(name);
                        field.setAccessible(true);
                        final Object edge = field.get(recyclerView);
                        final Field fEdgeEffect = edge.getClass().getDeclaredField("mEdgeEffect");
                        fEdgeEffect.setAccessible(true);
                        ((EdgeEffect) fEdgeEffect.get(edge)).setColor(color);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
