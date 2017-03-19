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
import xyz.klinker.android.drag_dismiss.delegate.DragDismissDelegate;

/**
 * An Activity that provides a Toolbar and drag-dismiss functionality for free. This Activity allows
 * you to add any content you want. Your content will be housed in a NestedScrollView, so you shouldn't
 * worry about the height of the content.
 */
public abstract class DragDismissActivity extends AbstractDragDismissActivity
        implements DragDismissDelegate.Callback {

    @Override
    protected AbstractDragDismissDelegate createDelegate() {
        return new DragDismissDelegate(this, this);
    }

    /**
     * Get the delegate instanced that is used to set up the {@link android.app.Activity}.
     *
     * @return the delegate instance.
     */
    public DragDismissDelegate getDragDismissDelegate() {
        return (DragDismissDelegate) delegate;
    }
}
