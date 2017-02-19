package xyz.klinker.android.drag_dismiss.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import xyz.klinker.android.drag_dismiss.DragDismissBundleBuilder;
import xyz.klinker.android.drag_dismiss.R;
import xyz.klinker.android.drag_dismiss.view.ElasticDragDismissFrameLayout;

/**
 * This is the common logic for the DragDismissableActivity and DragDismissableRecyclerViewActivity.
 * <p/>
 * You shouldn't extend this activity, you should extend its subclasses.
 */
public abstract class AbstractDragDismissActivity extends AppCompatActivity {

    protected abstract int getLayout();

    protected Toolbar toolbar;
    protected View statusBar;

    protected int primaryColor;
    protected String toolbarTitle;
    protected boolean shouldShowToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            extras = new Bundle();
        }

        String theme = extras.getString(DragDismissBundleBuilder.EXTRA_THEME);
        if (DragDismissBundleBuilder.Theme.LIGHT.name().equals(theme)) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if (DragDismissBundleBuilder.Theme.DARK.name().equals(theme)) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else if (DragDismissBundleBuilder.Theme.BLACK.name().equals(theme)) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
        }

        this.shouldShowToolbar = extras.getBoolean(DragDismissBundleBuilder.EXTRA_SHOULD_SHOW_TOOLBAR);
        this.toolbarTitle = extras.getString(DragDismissBundleBuilder.EXTRA_TOOLBAR_TITLE);
        this.primaryColor = getResources().getColor(
                extras.getInt(DragDismissBundleBuilder.EXTRA_PRIMARY_COLOR,
                        DragDismissBundleBuilder.DEFAULT_TOOLBAR_RESOURCE)
        );

        setContentView(getLayout());

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

        if (DragDismissBundleBuilder.Theme.BLACK.name().equals(theme)) {
            findViewById(R.id.dragdismiss_background_view).setBackgroundColor(Color.BLACK);
        }

        View.OnClickListener sideClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        };
        findViewById(R.id.dragdismiss_transparent_side_1).setOnClickListener(sideClickListener);
        findViewById(R.id.dragdismiss_transparent_side_2).setOnClickListener(sideClickListener);

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
}
