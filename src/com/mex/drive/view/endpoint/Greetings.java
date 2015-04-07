package com.mex.drive.view.endpoint;

import java.util.ArrayList;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.NotFoundException;

/**
 * Defines v1 of a helloworld API, which provides simple "greeting" methods.
 */
@Api(name = "helloworld", version = "v1")
public class Greetings {

  public static ArrayList<HelloGreeting> greetings = new ArrayList<HelloGreeting>();

  static {
    greetings.add(new HelloGreeting("hello world!"));
    greetings.add(new HelloGreeting("goodbye world!"));
  }

  public HelloGreeting getGreeting(@Named("id") Integer id) throws NotFoundException {
    try {
      return greetings.get(id);
    } catch (IndexOutOfBoundsException e) {
      throw new NotFoundException("Greeting not found with an index: " + id);
    }
  }

  public ArrayList<HelloGreeting> listGreeting() {
    return greetings;
  }
}