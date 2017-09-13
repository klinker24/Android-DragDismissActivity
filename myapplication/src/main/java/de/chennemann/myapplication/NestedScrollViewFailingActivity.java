package de.chennemann.myapplication;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import xyz.klinker.android.drag_dismiss.DragDismissIntentBuilder;
import xyz.klinker.android.drag_dismiss.activity.DragDismissActivity;



/**
 * This is the type of layout I would like to use in my Activity.
 * The reason for this is, that I experience a bit of lag without the additional Scroll View.
 * To reproduce the lag you can open the {@link FailingActivity} by clicking the "Start Failing Activity" button.
 * After that try quick drags from the top or the bottom after you have scrolled in the opposite direction or immediately after starting the activity.
 * It does not happen all of the time, but quite often.
 * With this activity I could not reproduce this behaviour, so I would like to use this one.
 */
public class NestedScrollViewFailingActivity extends DragDismissActivity {
    
    @Override
    public View onCreateContent(final LayoutInflater inflater, final ViewGroup parent, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_nested_scrollview_failing, parent, false);
    }
    
    static void start(final Context context) {
        final Intent intent = new Intent(context, NestedScrollViewFailingActivity.class);
    
        final Intent launchIntent = new DragDismissIntentBuilder(context)
            .setShouldScrollToolbar(false)
            .setShowToolbar(false)
            .setTheme(DragDismissIntentBuilder.Theme.LIGHT)
            .setPrimaryColorResource(R.color.colorPrimary)
            .build(intent);
        
        context.startActivity(launchIntent);
    }
}
