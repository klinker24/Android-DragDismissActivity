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
import android.view.MenuItem;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import xyz.klinker.android.drag_dismiss.delegate.AbstractDragDismissDelegate;

/**
 * This is the common logic for the DragDismissableActivity and DragDismissableRecyclerViewActivity.
 * <p/>
 * You shouldn't extend this activity, you should extend its subclasses.
 */
public abstract class AbstractDragDismissActivity extends AppCompatActivity {

    protected abstract AbstractDragDismissDelegate createDelegate();

    protected AbstractDragDismissDelegate delegate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.delegate = createDelegate();
        delegate.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Display the {@link ProgressBar}.
     */
    public void showProgressBar() {
        delegate.showProgressBar();
    }

    /**
     * Hide the {@link ProgressBar}.
     */
    public void hideProgressBar() {
        delegate.hideProgressBar();
    }
}
