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

package xyz.klinker.drag_dismiss;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import xyz.klinker.android.drag_dismiss.activity.DragDismissActivity;

public class DismissableActivityNormalContent extends DragDismissActivity {

    public static final String EXTRA_SHOW_PROGRESS = "extra_show_progress";

    @Override
    protected View onCreateContent(LayoutInflater inflater, ViewGroup parent) {
        final View v = inflater.inflate(R.layout.activity_scrollable, parent, false);
        final TextView tv = (TextView) v.findViewById(R.id.text_view);

        if (!shouldShowToolbar) {
            // don't need the padding that pushes it below the toolbar
            tv.setPadding(0,0,0,0);
        }

        if (getIntent().getBooleanExtra(EXTRA_SHOW_PROGRESS, false)) {
            showProgressBar();
            tv.setVisibility(View.GONE);

            tv.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tv.setVisibility(View.VISIBLE);
                    hideProgressBar();
                }
            }, 1500);
        }

        return v;
    }
}