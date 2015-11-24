package com.stevedunstan.jokes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class JokeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String joke = getIntent().getStringExtra("com.stevedunstan.jokes.JOKE");
        if (joke != null) {
            Toast.makeText(this, joke, Toast.LENGTH_SHORT).show();
        }
        finish(); // Don't display anything besides the toast.
    }
}
