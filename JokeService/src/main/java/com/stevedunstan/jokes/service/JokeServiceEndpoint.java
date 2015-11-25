/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.stevedunstan.jokes.service;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.stevedunstan.jokes.JokeRepository;

/** An endpoint class we are exposing */
@Api(
  name = "jokeService",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "service.jokes.stevedunstan.com",
    ownerName = "service.jokes.stevedunstan.com",
    packagePath=""
  )
)
public class JokeServiceEndpoint {

    @ApiMethod(name = "tellJoke")
    public Joke tellJoke() {
        JokeRepository repo = new JokeRepository();

        Joke response = new Joke();
        response.setJoke(repo.findJoke());

        return response;
    }

}
