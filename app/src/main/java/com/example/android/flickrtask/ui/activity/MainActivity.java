package com.example.android.flickrtask.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.flickrtask.R;
import com.example.android.flickrtask.services.MyService;
import com.example.android.flickrtask.ui.fragment.FlickerFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState==null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container ,new FlickerFragment())
                    .commit();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
