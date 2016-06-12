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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements FetchJokeAsyncTask.ResultCallback{
    private InterstitialAd mInterstitialAd;
    private LinearLayout mLoadingOverlay;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        Button jokeButton = (Button) root.findViewById(R.id.joke_button);
        jokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jokeButtonClicked(v);
            }
        });

        // Setup loading overlay
        mLoadingOverlay = (LinearLayout)root.findViewById(R.id.loading_overlay);

        // Setup interstitial ad
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });
        requestNewInterstitial();
        return root;
    }


    public void jokeButtonClicked(View view) {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
        showLoadingOverlay();
        new FetchJokeAsyncTask().execute(this);
    }

    public void onTaskResult(String result) {
        hideLoadingOverlay();
        Intent i = new Intent(getActivity(), JokeDisplayActivity.class);
        i.putExtra(JokeDisplayActivity.EXTRA_JOKE, result);
        startActivity(i);
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    private void showLoadingOverlay() {
        mLoadingOverlay.setVisibility(View.VISIBLE);
    }

    private void hideLoadingOverlay() {
        mLoadingOverlay.setVisibility(ViewStub.GONE);
    }
}
