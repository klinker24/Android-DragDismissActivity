package de.chennemann.myapplication;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;



public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        findViewById(R.id.startActivityButton)
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    FailingActivity.start(MainActivity.this);
                }
            });
        
        findViewById(R.id.startNestedScrollViewActivityButton)
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    NestedScrollViewFailingActivity.start(MainActivity.this);
                }
            });
        
    }
}
