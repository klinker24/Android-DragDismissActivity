package xyz.klinker.android.drag_dismiss.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import xyz.klinker.android.drag_dismiss.DragDismissIntentBuilder;
import xyz.klinker.android.drag_dismiss.R;
import xyz.klinker.android.drag_dismiss.util.ColorUtils;
import xyz.klinker.android.drag_dismiss.view.ElasticDragDismissFrameLayout;

/**
 * This is the common logic for the DragDismissableActivity and DragDismissableRecyclerViewActivity.
 * <p/>
 * You shouldn't extend this activity, you should extend its subclasses.
 */
public abstract class AbstractDragDismissActivity extends AppCompatActivity {

    protected abstract int getLayout();

    protected ProgressBar progressBar;
    protected Toolbar toolbar;
    protected View statusBar;

    protected int primaryColor;
    protected String toolbarTitle;
    protected boolean shouldShowToolbar;
    protected boolean shouldScrollToolbar;
    protected boolean fullscreenForTablets;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String theme = getIntent().getStringExtra(DragDismissIntentBuilder.EXTRA_THEME);
        if (DragDismissIntentBuilder.Theme.LIGHT.name().equals(theme)) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if (DragDismissIntentBuilder.Theme.DARK.name().equals(theme)) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else if (DragDismissIntentBuilder.Theme.BLACK.name().equals(theme)) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
        }

        this.fullscreenForTablets = getIntent().getBooleanExtra(DragDismissIntentBuilder.EXTRA_FULLSCREEN_FOR_TABLETS, false);
        this.shouldScrollToolbar = getIntent().getBooleanExtra(DragDismissIntentBuilder.EXTRA_SHOULD_SCROLL_TOOLBAR, true);
        this.shouldShowToolbar = getIntent().getBooleanExtra(DragDismissIntentBuilder.EXTRA_SHOULD_SHOW_TOOLBAR, true);
        this.toolbarTitle = getIntent().getStringExtra(DragDismissIntentBuilder.EXTRA_TOOLBAR_TITLE);
        this.primaryColor = getIntent().getIntExtra(DragDismissIntentBuilder.EXTRA_PRIMARY_COLOR,
                        DragDismissIntentBuilder.DEFAULT_TOOLBAR_RESOURCE);

        setContentView(getLayout());

        progressBar = (ProgressBar) findViewById(R.id.dragdismiss_loading);
        toolbar = (Toolbar) findViewById(R.id.dragdismiss_toolbar);
        statusBar = findViewById(R.id.dragdismiss_status_bar);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.dragdismiss_ic_close);
            getSupportActionBar().setTitle(toolbarTitle);
        }

        if (!shouldShowToolbar) {
            toolbar.setVisibility(View.GONE);
        }

        if (DragDismissIntentBuilder.Theme.BLACK.name().equals(theme)) {
            findViewById(R.id.dragdismiss_background_view).setBackgroundColor(Color.BLACK);
        }

        ColorUtils.changeProgressBarColors(progressBar, primaryColor);

        if (fullscreenForTablets) {
            findViewById(R.id.dragdismiss_transparent_side_1).setVisibility(View.GONE);
            findViewById(R.id.dragdismiss_transparent_side_2).setVisibility(View.GONE);

            View dragDismiss = findViewById(R.id.dragdismiss_drag_dismiss_layout);
            dragDismiss.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            dragDismiss.invalidate();
        } else {
            View.OnClickListener sideClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            };

            findViewById(R.id.dragdismiss_transparent_side_1).setOnClickListener(sideClickListener);
            findViewById(R.id.dragdismiss_transparent_side_2).setOnClickListener(sideClickListener);
        }

        ElasticDragDismissFrameLayout dragDismissLayout = (ElasticDragDismissFrameLayout)
                findViewById(R.id.dragdismiss_drag_dismiss_layout);
        dragDismissLayout.addListener(new ElasticDragDismissFrameLayout.ElasticDragDismissCallback() {
            @Override
            public void onDragDismissed() {
                super.onDragDismissed();
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
