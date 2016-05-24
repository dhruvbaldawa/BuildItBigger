package com.dhruvb.jokeactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeDisplayActivity extends AppCompatActivity {
    public static final String EXTRA_JOKE = "com.dhruvb.jokeactivity.JokeDisplayActivity.EXTRA_JOKE";
    public static TextView mJokeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);

        mJokeTextView = (TextView) findViewById(R.id.joke_text_view);
        Bundle extras = getIntent().getExtras();
        mJokeTextView.setText(extras.getString(EXTRA_JOKE));
    }
}
