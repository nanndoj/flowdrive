package com.mex.drive.view.endpoint;

public class HelloGreeting {

  public String message;

  public HelloGreeting() {
  };

  public HelloGreeting(String message) {
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
