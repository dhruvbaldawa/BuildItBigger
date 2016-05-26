package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.dhruvb.jokeprovider.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by dhruv on 25/5/16.
 */
public class FetchJokeAsyncTask extends AsyncTask<FetchJokeAsyncTask.ResultCallback, Void, String> {
    private static MyApi myApiService = null;
    private ResultCallback mResultCallback;

    public interface ResultCallback {
        void onTaskResult(String result);
    }

    @Override
    protected String doInBackground(ResultCallback... params) {
        if (myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
//                    .setRootUrl("https://default-demo-app-e87c.appspot.com/_ah/api")
//                    .setRootUrl("http://192.168.1.16:8080/_ah/api")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                            request.setDisableGZipContent(true);
                        }
                    });
            myApiService = builder.build();
        }

        mResultCallback = params[0];
        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        mResultCallback.onTaskResult(result);
    }
}
