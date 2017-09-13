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

import xyz.klinker.android.drag_dismiss.delegate.AbstractDragDismissDelegate;
import xyz.klinker.android.drag_dismiss.delegate.DragDismissRecyclerViewDelegate;

/**
 * Activity that allows you to drag and dismiss a RecyclerView, whenever you reach the top or
 * the bottom of the list.
 * <p/>
 * You will have to set up the RecyclerView in the abstract setupRecyclerView method. Within that
 * method, you should set the adapter and the LayoutManager.
 */
public abstract class DragDismissRecyclerViewActivity extends AbstractDragDismissActivity
        implements DragDismissRecyclerViewDelegate.Callback {

    @Override
    protected AbstractDragDismissDelegate createDelegate() {
        return new DragDismissRecyclerViewDelegate(this, this);
    }

    /**
     * Get the delegate instanced that is used to set up the {@link android.app.Activity}.
     *
     * @return the delegate instance.
     */
    public DragDismissRecyclerViewDelegate getDragDismissDelegate() {
        return (DragDismissRecyclerViewDelegate) delegate;
    }
}
