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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import xyz.klinker.android.drag_dismiss.DragDismissBundleBuilder;

public class SampleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        findViewById(R.id.scrollable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new DragDismissBundleBuilder()
                        .setTheme(DragDismissBundleBuilder.Theme.LIGHT)
                        .setPrimaryColorResource(R.color.colorPrimary)
                        .setToolbarTitle("Normal Activity Sample")
                        .build();

                Intent scrollable = new Intent(SampleActivity.this, SampleActivityScrollable.class);
                scrollable.putExtras(bundle);
                startActivity(scrollable);
            }
        });

        findViewById(R.id.dark).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new DragDismissBundleBuilder()
                        .setTheme(DragDismissBundleBuilder.Theme.DARK)
                        .setToolbarTitle("Dark Sample")
                        .build();

                Intent scrollable = new Intent(SampleActivity.this, SampleActivityScrollable.class);
                scrollable.putExtras(bundle);
                startActivity(scrollable);
            }
        });

        findViewById(R.id.dark_colored).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new DragDismissBundleBuilder()
                        .setTheme(DragDismissBundleBuilder.Theme.DARK)
                        .setPrimaryColorResource(R.color.colorPrimary)
                        .setToolbarTitle("Dark and Colored Sample")
                        .build();

                Intent scrollable = new Intent(SampleActivity.this, SampleActivityScrollable.class);
                scrollable.putExtras(bundle);
                startActivity(scrollable);
            }
        });

        findViewById(R.id.hide_toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new DragDismissBundleBuilder()
                        .setTheme(DragDismissBundleBuilder.Theme.LIGHT)
                        .setPrimaryColorResource(R.color.colorPrimary)
                        .setShowToolbar(false)
                        .setToolbarTitle("Hidden Toolbar Sample")
                        .build();

                Intent scrollable = new Intent(SampleActivity.this, SampleActivityScrollable.class);
                scrollable.putExtras(bundle);
                startActivity(scrollable);
            }
        });

        findViewById(R.id.recycler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new DragDismissBundleBuilder()
                        .setTheme(DragDismissBundleBuilder.Theme.LIGHT)
                        .setPrimaryColorResource(R.color.colorPrimary)
                        .setShowToolbar(true)
                        .setToolbarTitle("RecyclerView Sample")
                        .build();

                Intent recycler = new Intent(SampleActivity.this, SampleActivityRecycler.class);
                recycler.putExtras(bundle);
                startActivity(recycler);
            }
        });
    }
}


