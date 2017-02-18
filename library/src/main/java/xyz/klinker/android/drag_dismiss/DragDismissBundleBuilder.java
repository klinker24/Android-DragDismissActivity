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

import android.graphics.Color;
import android.os.Bundle;

public class DragDismissBundleBuilder {

    protected static final String EXTRA_THEME = "arg_base_theme";
    protected static final String EXTRA_PRIMARY_COLOR = "arg_primary_color";
    protected static final String EXTRA_TOOLBAR_TITLE = "arg_toolbar_title";
    protected static final String EXTRA_SHOULD_SHOW_TOOLBAR = "arg_show_toolbar";

    public static final int DEFAULT_TOOLBAR_COLOR = -1;

    public enum Theme {
        LIGHT, DARK, DAY_NIGHT
    }

    private Theme theme = Theme.DAY_NIGHT;
    private int primaryColor = DEFAULT_TOOLBAR_COLOR;
    private String toolbarTitle = null;
    private boolean shouldShowToolbar = true;

    public Bundle build() {
        Bundle bundle = new Bundle();

        bundle.putString(EXTRA_TOOLBAR_TITLE, toolbarTitle);
        bundle.putString(EXTRA_THEME, theme.name());
        bundle.putInt(EXTRA_PRIMARY_COLOR, primaryColor);
        bundle.putBoolean(EXTRA_SHOULD_SHOW_TOOLBAR, shouldShowToolbar);

        return bundle;
    }

    public DragDismissBundleBuilder setTheme(Theme theme) {
        this.theme = theme;
        return this;
    }

    public DragDismissBundleBuilder setPrimaryColor(int primaryColor) {
        this.primaryColor = primaryColor;
        return this;
    }

    public DragDismissBundleBuilder setToolbarTitle(String title) {
        this.toolbarTitle = title;
        return this;
    }

    public DragDismissBundleBuilder setShowToolbar(boolean showToolbar) {
        this.shouldShowToolbar = showToolbar;
        return this;
    }
}
