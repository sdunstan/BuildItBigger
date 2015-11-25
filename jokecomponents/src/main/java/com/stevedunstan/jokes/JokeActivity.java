package com.stevedunstan.jokes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class JokeActivity extends AppCompatActivity {

    public static final String JOKE_TEXT = "com.stevedunstan.jokes.JOKE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String joke = getIntent().getStringExtra(JOKE_TEXT);
        if (joke != null) {
            Toast.makeText(this, joke, Toast.LENGTH_SHORT).show();
        }
        finish(); // Don't display anything besides the toast.
    }
}
