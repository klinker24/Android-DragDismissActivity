package de.chennemann.myapplication;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import xyz.klinker.android.drag_dismiss.DragDismissIntentBuilder;
import xyz.klinker.android.drag_dismiss.activity.DragDismissActivity;



public class FailingActivity extends DragDismissActivity {
    
    @Override
    public View onCreateContent(final LayoutInflater inflater, final ViewGroup parent, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_failing, parent, false);
    }
    
    static void start(final Context context) {
        final Intent intent = new Intent(context, FailingActivity.class);
    
        final Intent launchIntent = new DragDismissIntentBuilder(context)
            .setShouldScrollToolbar(false)
            .setShowToolbar(false)
            .setTheme(DragDismissIntentBuilder.Theme.LIGHT)
            .setPrimaryColorResource(R.color.colorPrimary)
            .build(intent);
        
        context.startActivity(launchIntent);
    }
}
