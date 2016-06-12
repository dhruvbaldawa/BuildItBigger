package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;

import com.dhruvb.jokeactivity.JokeDisplayActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements FetchJokeAsyncTask.ResultCallback{
    private LinearLayout mLoadingOverlay;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        Button jokeButton = (Button) root.findViewById(R.id.joke_button);
        jokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jokeButtonClicked(v);
            }
        });

        // Setup loading overlay
        mLoadingOverlay = (LinearLayout)root.findViewById(R.id.loading_overlay);
        return root;
    }


    public void jokeButtonClicked(View view) {
        showLoadingOverlay();
        new FetchJokeAsyncTask().execute(this);
    }

    public void onTaskResult(String result) {
        hideLoadingOverlay();
        Intent i = new Intent(getActivity(), JokeDisplayActivity.class);
        i.putExtra(JokeDisplayActivity.EXTRA_JOKE, result);
        startActivity(i);
    }

    private void showLoadingOverlay() {
        mLoadingOverlay.setVisibility(View.VISIBLE);
    }

    private void hideLoadingOverlay() {
        mLoadingOverlay.setVisibility(ViewStub.GONE);
    }
}
