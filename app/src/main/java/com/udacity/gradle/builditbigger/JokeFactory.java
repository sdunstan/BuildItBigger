package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.stevedunstan.jokes.JokeActivity;
import com.stevedunstan.jokes.service.jokeService.JokeService;

import java.io.IOException;

class JokeFactory {
    private static final String TAG = JokeFactory.class.getCanonicalName();

    private JokeListener listener;

    public JokeFactory(JokeListener listener) {
        this.listener = listener;

    }

    public void getJoke(Context context) {
        Log.d(TAG, "Getting joke");
        new JokeTask().execute(context);
    }

    private class JokeTask extends AsyncTask<Context, Void, String> {
        private Context context;
        private JokeService jokeService;

        @Override
        protected String doInBackground(Context... params) {
            Log.d(TAG, "Starting joke service");
            this.context = params[0];
            if (jokeService == null) {
                JokeService.Builder builder = new JokeService.Builder(
                        AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null);
                builder.setRootUrl("https://joke-server.appspot.com/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });

                jokeService = builder.build();
            }

            String jokeText;
            try {
                jokeText = jokeService.tellJoke().execute().getJoke();
            } catch (IOException e) {
                jokeText = "Now a really funny one: " + e.getMessage();
            }

            return jokeText;
        }

        @Override
        protected void onPostExecute(String result) {
            Intent jokeIntent = new Intent(context, JokeActivity.class);
            jokeIntent.putExtra(JokeActivity.JOKE_TEXT, result);
            context.startActivity(jokeIntent);
            listener.jokeDelivered();
        }
    }

}
