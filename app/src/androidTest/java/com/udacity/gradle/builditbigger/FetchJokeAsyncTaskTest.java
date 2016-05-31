
package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;

/**
 * Created by dhruv on 26/5/16.
 */
public class FetchJokeAsyncTaskTest extends AndroidTestCase{
    public void testTaskExecution() throws InterruptedException {
        final Object syncObject = new Object();
        new FetchJokeAsyncTask().execute(new FetchJokeAsyncTask.ResultCallback() {
            @Override
            public void onTaskResult(String result) {
                assertTrue("result length should be non-zero", result.length() != 0);
                synchronized (syncObject) {
                    syncObject.notify();
                }
            }
        });
        // wait for the callback to be called
        synchronized (syncObject) {
            syncObject.wait();
        }
    }
}
