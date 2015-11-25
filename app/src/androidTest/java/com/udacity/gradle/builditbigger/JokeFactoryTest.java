package com.udacity.gradle.builditbigger;

import android.app.Instrumentation;
import android.test.SingleLaunchActivityTestCase;

import com.stevedunstan.jokes.JokeActivity;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class JokeFactoryTest extends SingleLaunchActivityTestCase<MainActivity> implements JokeListener{

    CountDownLatch signal;

    public JokeFactoryTest() {
        super("com.udacity.gradle.builditbigger", MainActivity.class);
    }

    public void testVerifyJokeTaskReturns() throws Throwable {
        // Need to setup a thread countdownlatch so that
        signal = new CountDownLatch(1);
        Instrumentation.ActivityMonitor receiverActivityMonitor =
                getInstrumentation().addMonitor(JokeActivity.class.getName(),
                        null, false);

        // Our JokeFactory is designed to encapsulate the asynchronous call to the service and
        // notify callers with a listener. The listener is a good place to put a hook for our
        // CountDownLatch.
        final JokeFactory factory = new JokeFactory(this);

        // The test must be run on ther UI thread otherwise we don't get the AsyncTask.onPostExecute callback
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                factory.getJoke(getActivity());
            }
        });
        signal.await(10, TimeUnit.SECONDS); // Service should be called within 10 seconds

        // JokeActivity should be launched within a second after we are notified that the joke was delivered.
        JokeActivity jokeActivity = (JokeActivity) receiverActivityMonitor.waitForActivityWithTimeout(1000);

        // Assert that the joke was delivered to the JokeActivity's intent parameter
        assertNotNull("JokeActivity was never called (null)", jokeActivity);
        String jokeText = jokeActivity.getIntent().getStringExtra(JokeActivity.JOKE_TEXT);
        assertEquals("Why did the chicken cross the road? To get to the other side.", jokeText); // Hahahahahahhahahahahahhaha

    }

    @Override
    public void jokeDelivered() {
        signal.countDown();
    }
}
