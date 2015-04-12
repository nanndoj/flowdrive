package com.mex.drive.view.endpoint;

/**
 * Contains the client IDs and scopes for allowed clients consuming the
 * helloworld API.
 */
public class Constants {
  public static final String WEB_CLIENT_ID = "592974826606-g3fo48v2eb78ql47dde6hfrlcr6vuo3f.apps.googleusercontent.com";
  public static final String SECRET_KEY    = "2f2iuyNyMBESDkBU4inYoH3e";

  public static final String EMAIL_SCOPE   = "https://www.googleapis.com/auth/userinfo.email";
  public static final String CALLBACK_URL  = "http://localhost:8888/googleoauth2callback";
}
